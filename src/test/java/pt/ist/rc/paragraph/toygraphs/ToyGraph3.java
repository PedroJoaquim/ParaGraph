package pt.ist.rc.paragraph.toygraphs;

import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.model.Vertex;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Pedro Joaquim on 27-10-2016
 */
public class ToyGraph3 {

    /*
    *
    *
    *     (0)--------|
    *      |         |
    *      |         |----------------------
    *      |         |                     |
    *     (1)-------(2)--------(3)--------(4)
    *
    */
    public static GraphData<Void, Void> loadGraph(){

        Vertex<Void, Void>[] vertices = new Vertex[5];


        Edge<Void>[] vertice0Edges = new Edge[2];
        vertice0Edges[0] = new Edge<>(1, null);
        vertice0Edges[1] = new Edge<>(2, null);

        Edge<Void>[] vertice1Edges = new Edge[2];
        vertice1Edges[0] = new Edge<>(0, null);
        vertice1Edges[1] = new Edge<>(2, null);


        Edge<Void>[] vertice2Edges = new Edge[4];
        vertice2Edges[0] = new Edge<>(0, null);
        vertice2Edges[1] = new Edge<>(1, null);
        vertice2Edges[2] = new Edge<>(3, null);
        vertice2Edges[3] = new Edge<>(4, null);


        Edge<Void>[] vertice3Edges = new Edge[2];
        vertice3Edges[0] = new Edge<>(2, null);
        vertice3Edges[1] = new Edge<>(4, null);

        Edge<Void>[] vertice4Edges = new Edge[2];
        vertice4Edges[0] = new Edge<>(3, null);
        vertice4Edges[1] = new Edge<>(2, null);


        vertices[0] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice0Edges)));
        vertices[1] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice1Edges)));
        vertices[2] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice2Edges)));
        vertices[3] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice3Edges)));
        vertices[4] = new Vertex<>(null,  new ArrayList<>(Arrays.asList(vertice4Edges)));

        return new GraphData<Void, Void>(vertices);
    }
}
