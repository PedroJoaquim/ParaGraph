package pt.ist.rc.paragraph.toygraphs;

import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.model.Vertex;

import java.util.ArrayList;
import java.util.Arrays;

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
    public static GraphData<Void, Integer> loadGraph(){

        Vertex<Void, Integer>[] vertices = new Vertex[6];


        Edge<Integer>[] vertice0Edges = new Edge[2];
        vertice0Edges[0] = new Edge<>(1, 4);
        vertice0Edges[1] = new Edge<>(2, 15);

        Edge<Integer>[] vertice1Edges = new Edge[2];
        vertice1Edges[0] = new Edge<>(2, 5);
        vertice1Edges[1] = new Edge<>(3, 10);


        Edge<Integer>[] vertice2Edges = new Edge[1];
        vertice2Edges[0] = new Edge<>(4, 3);


        Edge<Integer>[] vertice3Edges = new Edge[2];
        vertice3Edges[0] = new Edge<>(1, 10);
        vertice3Edges[1] = new Edge<>(5, 11);

        Edge<Integer>[] vertice4Edges = new Edge[1];
        vertice4Edges[0] = new Edge<>(3, 4);

        Edge<Integer>[] vertice5Edges = new Edge[0];

        vertices[0] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice0Edges)));
        vertices[1] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice1Edges)));
        vertices[2] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice2Edges)));
        vertices[3] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice3Edges)));
        vertices[4] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice4Edges)));
        vertices[5] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice5Edges)));

        return new GraphData<Void, Integer>(vertices);
    }
}
