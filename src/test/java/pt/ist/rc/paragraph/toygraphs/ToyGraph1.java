package pt.ist.rc.paragraph.toygraphs;

import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.model.Vertex;

/**
 * Created by Pedro Joaquim on 17-10-2016
 */
public class ToyGraph1 {


    /*
     *          (1) - - - - - (4)
     *           |             |
      *          |             |        (5) - - - - - (6)       (8) - - - - (9)
     *           |             |                       |
     *           |             |                       |
     *          (2) - - - - - (3)                     (7)
     *
     *
     *
     */
    public static GraphData<Void, Void> loadGraph(){

        Vertex<Void, Void>[] vertices = new Vertex[9];


        Edge<Void>[] vertice1Edges = new Edge[2];
        vertice1Edges[0] = new Edge<>(2, null);
        vertice1Edges[1] = new Edge<>(4, null);

        Edge<Void>[] vertice2Edges = new Edge[2];
        vertice2Edges[0] = new Edge<>(1, null);
        vertice2Edges[1] = new Edge<>(3, null);

        Edge<Void>[] vertice3Edges = new Edge[2];
        vertice3Edges[0] = new Edge<>(2, null);
        vertice3Edges[1] = new Edge<>(4, null);

        Edge<Void>[] vertice4Edges = new Edge[2];
        vertice4Edges[0] = new Edge<>(1, null);
        vertice4Edges[1] = new Edge<>(3, null);

        Edge<Void>[] vertice5Edges = new Edge[1];
        vertice5Edges[0] = new Edge<>(6, null);

        Edge<Void>[] vertice6Edges = new Edge[2];
        vertice6Edges[0] = new Edge<>(5, null);
        vertice6Edges[1] = new Edge<>(7, null);

        Edge<Void>[] vertice7Edges = new Edge[1];
        vertice7Edges[0] = new Edge<>(6, null);

        Edge<Void>[] vertice8Edges = new Edge[1];
        vertice8Edges[0] = new Edge<>(9, null);

        Edge<Void>[] vertice9Edges = new Edge[1];
        vertice9Edges[0] = new Edge<>(8, null);



        vertices[0] = new Vertex<>(null, vertice1Edges);
        vertices[1] = new Vertex<>(null, vertice2Edges);
        vertices[2] = new Vertex<>(null, vertice3Edges);
        vertices[3] = new Vertex<>(null, vertice4Edges);
        vertices[4] = new Vertex<>(null, vertice5Edges);
        vertices[5] = new Vertex<>(null, vertice6Edges);
        vertices[6] = new Vertex<>(null, vertice7Edges);
        vertices[7] = new Vertex<>(null, vertice8Edges);
        vertices[8] = new Vertex<>(null, vertice9Edges);


        return new GraphData<Void, Void>(vertices);
    }

}
