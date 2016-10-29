package pt.ist.rc.paragraph.toygraphs;

import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.Graph;
import pt.ist.rc.paragraph.model.Vertex;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Pedro Joaquim.
 */
public class ToyGraph2 {

    /*
    *                   10        11
    *      - 4 - ->(1)- - - (3) - - -> (5)
    *     |         |         ^
    *     |         |         |
    *    (0)      5 |       4 |
    *     |         |         |
    *     |         \/        |
    *      - - - ->(2) - - ->(4)
    *        15            3
    *
    */
    public static Graph<Void, Integer> loadGraph() {
        List<Edge<Integer>> vertex0Edges = Arrays.asList(
                new Edge.Builder<Integer>().targetIdx(1).value(4).build(),
                new Edge.Builder<Integer>().targetIdx(2).value(15).build()
        );

        List<Edge<Integer>> vertex1Edges = Arrays.asList(
                new Edge.Builder<Integer>().targetIdx(2).value(5).build(),
                new Edge.Builder<Integer>().targetIdx(3).value(10).build()
        );

        List<Edge<Integer>> vertex2Edges = Arrays.asList(
                new Edge.Builder<Integer>().targetIdx(4).value(3).build()
        );

        List<Edge<Integer>> vertex3Edges = Arrays.asList(
                new Edge.Builder<Integer>().targetIdx(1).value(10).build(),
                new Edge.Builder<Integer>().targetIdx(5).value(11).build()
        );

        List<Edge<Integer>> vertex4Edges = Arrays.asList(
                new Edge.Builder<Integer>().targetIdx(3).value(4).build()
        );

        List<Edge<Integer>> vertex5Edges = Arrays.asList();


        List<Vertex<Void, Integer>> vertices = Arrays.asList(
                new Vertex.Builder<Void, Integer>().addAllEdges(vertex0Edges).build(),
                new Vertex.Builder<Void, Integer>().addAllEdges(vertex1Edges).build(),
                new Vertex.Builder<Void, Integer>().addAllEdges(vertex2Edges).build(),
                new Vertex.Builder<Void, Integer>().addAllEdges(vertex3Edges).build(),
                new Vertex.Builder<Void, Integer>().addAllEdges(vertex4Edges).build(),
                new Vertex.Builder<Void, Integer>().addAllEdges(vertex5Edges).build()
        );

        return new Graph.SimpleBuilder<Void, Integer>().addVertices(vertices).build();
    }
}
