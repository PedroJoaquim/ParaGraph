package pt.ist.rc.paragraph.model;

import java.io.File;
import java.util.function.Function;

public class GraphData<VV, EV> {

    /* vertex ids go from 0 to num vertices -1
     * and the id is implicit by its position on the vertices array
     *
     */
    private Vertex<VV, EV>[] vertices;

    public GraphData(File inputDataFile, Function<String, VV> loadVertexDataFunction, Function<String, EV> loadEdgeDataFunction){
        loadGraphData(inputDataFile, loadVertexDataFunction, loadEdgeDataFunction);
    }

    // for toy graphs
    public GraphData(Vertex<VV, EV>[] vertices){
        this.vertices = vertices;
    }

    private void loadGraphData(File inputDataFile, Function<String, VV> loadVertexDataFunction, Function<String, EV> loadEdgeDataFunction) {
        /*Load Graph Data Here (create vertices and edges arrays)

            input file:

            <num vertices>
            <vertex 0 value>|<vertex 0 first out neighbor>|<edge0 property>|<vertex 0 second out neighbor>|<edge1 property>
            <vertex 1 value>|<vertex 1 first out neighbor>|<edge2 property>|<vertex 1 second out neighbor>|<edge3 property>
            ...
            <vertex n value>|<vertex n first out neighbor>|<edgeY property>|<vertex n second out neighbor>|<edgeW property>


            apply function loadVertexDataFunction to <vertex i value>
            apply function loadEdgeDataFunction to <edge i property>

         */
    }

    public Vertex<VV, EV> getVertex(int vertexID){
        return vertices[vertexID];
    }

    public Vertex<VV, EV>[] getVertices() {
        return vertices;
    }
}
