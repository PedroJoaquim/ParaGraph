package pt.ist.rc.paragraph.model;

import java.util.Iterator;

/**
 * Created by Pedro Joaquim.
 */

public abstract class Vertex<VV, EV, MV> {

    /*
     * Vertex Identifier
     */
    private int id;

    /*
     * Vertex outgoing edges
     */
    private Edge<EV>[] outEdges;

    /*
     * Vertex computational value
     */
    private VV value;

    public Vertex(int id, Edge<EV>[] outEdges) {
        this.id = id;
        this.outEdges = outEdges;
        this.value = initialValue();
    }

    public abstract void compute(Iterator<Message<MV>> msgs);
    public abstract VV initialValue();

    public void voteToHalt() {
        //todo
    }

    public void sendMessageTo(int targetID, MV msg){
        //todo
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VV getValue() {
        return value;
    }

    public void setValue(VV value) {
        this.value = value;
    }
}
