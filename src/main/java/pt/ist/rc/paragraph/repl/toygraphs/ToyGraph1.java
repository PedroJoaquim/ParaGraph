package pt.ist.rc.paragraph.repl.toygraphs;

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


        vertices[0] = new Vertex<>(null, vertice1Edges);


        return null; //todo



    }

}
