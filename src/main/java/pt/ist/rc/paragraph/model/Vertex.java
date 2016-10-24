package pt.ist.rc.paragraph.model;

import java.util.Iterator;
import java.util.List;

public class Vertex<VV, EV> {

    /**
     * Vertex property associated to the graph
     */
    private VV value;

    /**
     * Vertex outgoing edges
     */
    private List<Edge<EV>> outEdges;

    public Vertex(VV value, List<Edge<EV>> outEdges) {
        this.value = value;
        this.outEdges = outEdges;
    }

    public VV getValue() {
        return value;
    }

    public List<Edge<EV>> getOutEdges() {
        return outEdges;
    }
}
