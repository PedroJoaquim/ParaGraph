ParaGraph - Parallel Graph Processing Library [![Build Status](https://travis-ci.org/PedroJoaquim/ParaGraph.svg?branch=master)](https://travis-ci.org/PedroJoaquim/ParaGraph)
================================

## Introduction

**ParaGraph** is a simple, single machine, graph processing library.

This systems aims at helping developers creating easy to use and parallelize graph algorithms that can run over a huge graph. The developed algorithms are based on the Google's Pregel "Think Like a Vertex" programming model and are execute under the Bulk Synchronous Parallel computational model.

The computation runs by supersteps, every superstep a `compute()` function is executed in parallel at each vertex. Every vertex is able to update its value and send messages to its outgoing neighbors. Messages sent during superstep N will be available in superstep N+1. The computation ends when all vertices have called the `voteToHalt()` function. Vertices that vote to halt in one superstep  but receive messages in later supersteps are reactivated

## How to Use

### Add Project to CLASSPATH

The first step is to add our _ParaGraph*.jar_ file to you project _CLASSPATH_.
This can be achieved by running your project with the following command:

`java -cp "/path/to/jar/Paragraph*.jar" your.project.MainClass`

### Loading the Graph

Before starting executing algorithms on your graph data you need to be able to load it. To do so, you need to have a `Graph<VV, EV>` instance.

This class will represent your graph data and can be used to execute multiple algorithms. This graph data is immutable so you are not able to change it, you can hava multiple instances of `Graph` though.


The Graph class is a generic type, parameterized by the `VV` and `EV` value types.

The parameters refer to graph vertex's properties (VV) and edge's properties (EV). For instance a directed graph from persons, with names and phone numbers, to other persons linked by an edge with a given weight could be loaded by a `Graph<Person, Integer>` instance. This data will then be available to you during algorithm execution.

In order to load a `Graph` from a GML formated file it is necessary to create a `GraphLoader<VV, EV>` instance.
With this instance you need to call the `fromFile` method, supplying 3 parameters.
The first parameter is a file path (a `String`) to the GML formated file holding the graph information.
The second and third parameter are `Function` objects that receive a `String` as input and return `VV` and `EV`, respectivly.

Our GML parser implementation supports a special property named "value", on `node` and `edge` GML elements, that provides the ability to load values specific to your needs. The parser reads the "values" as `String` and, on loading this information to the `Graph`, invokes the `Function` objects.
This is necessary because we don't know how to parse the input file properties into the objects that you put there.

An example usage could be:

```java
  ...
  const String filePath = "/usr/test/documents/graph/graph1.data";

   Function<String, Person> fPerson = new Function<String, Person>() {
       @Override
       public Person apply(String s) {
           String[] splited = s.split("#");
           return new Person(splited[0], splited[1]); //name and phone number
       }
   };

   Function<String, Integer> fInteger = new Function<String, Integer>() {
       @Override
       public Integer apply(String s) {
           return Integer.valueOf(s); //edge weight
       }
   };

  Graph<Person, Integer> graph = new GraphLoader<Person, Integer>().fromFile(filePath, fPerson, fInteger);
  ...
```



### Creating Your Own Algorithms


#### Extending VertexCentricComputation

In order to develop your own algorithms you have to create your own class and extend:

`VertexCentricComputation<VV, EV, VCV, MV>`

This class is also a generic type parameterized by the values `VV`, `EV`, `VCV` and `MV` that you need to define when you extend this class.

As in `Graph`, `VV` and `EV` represent the core graph data vertex's values and edges's values.

Lets imagine that your algorithm is designed to process graphs that have vertex properties with `Person` and `Integer` as edges properties, then you should extend the `VertexCentricComputation<Person, Integer, ..., ...>`. But if your algorithm does not need to know the vertex properties nor edge properties you can extend the `VertexCentricComputation<Object, Object, ..., ...>`, this way you are able to receive any kind of `Graph` instance.

The `MV` and `VCV` are values specific to the algorithm being created only.

`MV` represents the message values that your algorithm will exchange between vertices. For instance, in our single source shortest paths algorithm we exchange distances in messages, so our MV values is defined as `Integer`.

`VCV` values represent the vertices algorithm internal state. They define the class that represents the state of the vertex during the algorithm execution and that can be checked after execution. For example, in our Pagerank algorithm implementation we defined our `VCV` value as `Double` and it represents the vertex pagerank value.

#### Implementing compute and initialize

When creating your own algorithm class, you will be forced to implement two methods, the `compute(Vertex v)` and the `initialize(int vertexID)` methods.

The `compute(Vertex v)` function holds the algorithm implementation and receives as parameter a `Vertex` instance that represents the current vertex being executed.

The `initialize(int vertexID)` function defines a initial value to the vertices (should return a value compatible with the defined `VCV`).


#### Putting it all together

here we give concrete examples of algorithms representing our implementation of the pagerank and triangle counting algorithms.
More algorithms can be found at the _analytics_ package

```java
public class PageRankVertexComputation extends VertexCentricComputation<Object, Object, Double, Double> {

    private int superstepNumber;

    public PageRankVertexComputation(Graph<?, ?> graphData, ComputationConfig config, int supserstepNumber) {
        super(graphData, config);

        this.superstepNumber = supserstepNumber;
    }

    @Override
    protected Double initializeValue(int vertexID) {
        return 1.0 / getNumVertices();
    }

    @Override
    protected void compute(ComputationalVertex<?, ?, Double, Double> vertex) {

        int numOutEdges = vertex.getNumberOutEdges();

        if(getSuperStep() > 0){

            double sum = 0;

            for (Double msg : vertex.getMessages()) {
                sum += msg;
            }

            vertex.setComputationalValue((0.15 / getNumVertices()) + 0.85 * sum);
        }

        if(getSuperStep() < superstepNumber){

            sendMessageToAllOutNeighbors(vertex, vertex.getComputationalValue() / numOutEdges);

        } else {

           vertex.voteToHalt();
        }
    }
}
```

```java

public class SimpleTriangleCountingAlgorithm extends VertexCentricComputation<Object, Object, Integer, String> {

    public SimpleTriangleCountingAlgorithm(Graph<?, ?> graphData, ComputationConfig config) {
        super(graphData, config);
    }

    @Override
    protected Integer initializeValue(int vertexID) {
        return 0;
    }

    @Override
    protected void compute(ComputationalVertex<?, ?, Integer, String> vertex) {


        if (getSuperStep() == 0) {

            Iterator<? extends Edge<?>> iterator = vertex.getOutEdgesIterator();

            while (iterator.hasNext()) {
                Edge<?> edge = iterator.next();

                if (edge.getTarget() > vertex.getId()) {
                    sendMessageTo(edge.getTarget(), String.valueOf(vertex.getId()));
                }
            }

        } else if (getSuperStep() == 1) {

            Iterator<? extends Edge<?>> iterator = vertex.getOutEdgesIterator();

            List<String> messages = vertex.getMessages();

            while (iterator.hasNext()) {
                Edge<?> edge = iterator.next();

                if (edge.getTarget() > vertex.getId()) {

                    for (String msg : messages) {
                        sendMessageTo(edge.getTarget(), msg + "#" + String.valueOf(vertex.getId()));
                    }
                }
            }


        } else {

            for (String msg : vertex.getMessages()) {

                String[] verticesID = msg.split("#");

                Iterator<? extends Edge<?>> iterator = vertex.getOutEdgesIterator();

                while (iterator.hasNext()) {
                    Edge<?> edge = iterator.next();

                    if (Integer.valueOf(verticesID[0]) == edge.getTarget()) {
                        vertex.setComputationalValue(vertex.getComputationalValue() + 1);
                        break;
                    }
                }
            }
        }

        vertex.voteToHalt();
    }


    public int getTriangleCount(){
        return getVertexComputationalValues().stream().mapToInt(Integer::intValue).sum();
    }
}
```

### Executing Algorithms

Once you have loaded the graph data and created your own algorithm, or decided to use one of ours already implemented algorithms, you are ready to put things moving.

Here we leave an example app illustrating  all the described functionalities:


```java

import pt.ist.rc.paragraph.analytics.SimpleTriangleCountingAlgorithm;
import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.model.Graph;

import java.util.function.Function;

public class TestGraph {
    public static void main(String[] args) {
        const String filePath = "C:/users/test/documents/graph/graph1";

        Function<String, Void> f = new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                return null;
            }
        };

        ComputationConfig conf = new ComputationConfig().setNumWorkers(5);

        Graph<Void, Void> graph = new GraphLoader<Void, Void>().fromFile(filePath, f, f);


        SimpleTriangleCountingAlgorithm stc = new SimpleTriangleCountingAlgorithm(graph, conf);
        stc.execute();

        System.out.println(stc.getTriangleCount());
    }

}


```
