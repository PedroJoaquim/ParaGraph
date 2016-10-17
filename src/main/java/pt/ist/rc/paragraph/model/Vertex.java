package pt.ist.rc.paragraph.model;

public class Vertex<VV, EV> {

    /*
     * Vertex Property
     */
    private VV value;

    /*
     * Vertex outgoing edges
     */
    private Edge<EV>[] outEdges;

    public Vertex(VV value, Edge<EV>[] outEdges) {
        this.value = value;
        this.outEdges = outEdges;
    }

    public VV getValue() {
        return value;
    }

    public Edge<EV>[] getOutEdges() {
        return outEdges;
    }
}
