package pt.ist.rc.paragraph.toygraphs;

import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.Graph;
import pt.ist.rc.paragraph.model.Vertex;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Pedro Joaquim on 17-10-2016
 */
public class ToyGraph1 {


    /*
     *          (0) - - - - - (3)
     *           |             |
      *          |             |        (4) - - - - - (5)       (7) - - - - (8)
     *           |             |                       |
     *           |             |                       |
     *          (1) - - - - - (2)                     (6)
     *
     *
     *
     */
    public static Graph<Void, Void> loadGraph() {
        List<Edge<Void>> vertex0Edges = Arrays.asList(
                new Edge.Builder<Void>().targetIdx(1).build(),
                new Edge.Builder<Void>().targetIdx(3).build()
        );

        List<Edge<Void>> vertex1Edges = Arrays.asList(
                new Edge.Builder<Void>().targetIdx(0).build(),
                new Edge.Builder<Void>().targetIdx(2).build()
        );

        List<Edge<Void>> vertex2Edges = Arrays.asList(
                new Edge.Builder<Void>().targetIdx(1).build(),
                new Edge.Builder<Void>().targetIdx(3).build()
        );

        List<Edge<Void>> vertex3Edges = Arrays.asList(
                new Edge.Builder<Void>().targetIdx(0).build(),
                new Edge.Builder<Void>().targetIdx(2).build()
        );

        List<Edge<Void>> vertex4Edges = Arrays.asList(
                new Edge.Builder<Void>().targetIdx(5).build()
        );

        List<Edge<Void>> vertex5Edges = Arrays.asList(
                new Edge.Builder<Void>().targetIdx(4).build(),
                new Edge.Builder<Void>().targetIdx(6).build()
        );

        List<Edge<Void>> vertex6Edges = Arrays.asList(
                new Edge.Builder<Void>().targetIdx(5).build()
        );

        List<Edge<Void>> vertex7Edges = Arrays.asList(
                new Edge.Builder<Void>().targetIdx(8).build()
        );

        List<Edge<Void>> vertex8Edges = Arrays.asList(
                new Edge.Builder<Void>().targetIdx(7).build()
        );


        List<Vertex<Void, Void>> vertices = Arrays.asList(
                new Vertex.Builder<Void, Void>().addAllEdges(vertex0Edges).build(),
                new Vertex.Builder<Void, Void>().addAllEdges(vertex1Edges).build(),
                new Vertex.Builder<Void, Void>().addAllEdges(vertex2Edges).build(),
                new Vertex.Builder<Void, Void>().addAllEdges(vertex3Edges).build(),
                new Vertex.Builder<Void, Void>().addAllEdges(vertex4Edges).build(),
                new Vertex.Builder<Void, Void>().addAllEdges(vertex5Edges).build(),
                new Vertex.Builder<Void, Void>().addAllEdges(vertex6Edges).build(),
                new Vertex.Builder<Void, Void>().addAllEdges(vertex7Edges).build(),
                new Vertex.Builder<Void, Void>().addAllEdges(vertex8Edges).build()
        );

        return new Graph.SimpleBuilder<Void, Void>().addVertices(vertices).build();
    }

}
