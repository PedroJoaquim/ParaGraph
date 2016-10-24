package pt.ist.rc.paragraph.computation;

import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Vertex Instance associated with a given computation
 */
public class ComputationalVertex<VV, EV, VCV, MV> {

    /**
     * Vertex id from core vertex
     */
    private int id;

    /**
     * Value associated with a specific vertex centric computation
     */
    private VCV computationalValue;

    /**
     * Vertex core data
     */
    private Vertex<VV, EV> coreVertex;

    /**
     * Messages received from previous superstep
     */
    private List<MV> inbox;

    /**
     * Messages for next superstep
     */
    private List<MV> nextStepInbox;

    /**
     * Set of active vertices
     */
    private final HashSet<Integer> activeVertices;

    /**
     * Constructor
     *
     * @param id Core vertex id
     * @param computationalValue Initial vertex computational value
     * @param coreVertex Core vertex data
     * @param activeVertices Active vertices set
     */
    public ComputationalVertex(int id, VCV computationalValue, Vertex<VV, EV> coreVertex, HashSet<Integer> activeVertices) {
        this.id = id;
        this.computationalValue = computationalValue;
        this.coreVertex = coreVertex;
        this.inbox = new ArrayList<MV>();
        this.nextStepInbox = new ArrayList<MV>();
        this.activeVertices = activeVertices;
    }


    /**
     * @return Core vertex id associated with this instance
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return Outgoing Edges from the core vertex
     */
    public Iterator<Edge<EV>> getOutEdgesIterator(){
        return coreVertex.getOutEdges().iterator();
    }

    /**
     *
     * @return Number of outgoing edges
     */
    public int getNumberOutEdges(){
        return coreVertex.getOutEdges().size();
    }

    /**
     *
     * @return Current computational value associated with this vertex
     */
    public VCV getComputationalValue() {
        return computationalValue;
    }

    /**
     *
     * @param computationalValue Set current vertex computational value
     */
    public void setComputationalValue(VCV computationalValue) {
        this.computationalValue = computationalValue;
    }

    /**
     *
     * @param msg Add new message for next superstep
     */
    public synchronized void addNextStepMessage(MV msg){
        this.nextStepInbox.add(msg);
    }

    /**
     * Prepare messages inbox for next superstep
     */
    public void swapInboxes(){
        this.inbox = nextStepInbox;
        this.nextStepInbox = new ArrayList<MV>();
    }

    /**
     * Vote to halt computation (all vertices need to halt in order to stop computation)
     */
    public void voteToHalt(){
        synchronized (activeVertices){
            activeVertices.remove(this.id);
        }
    }

    /**
     * Check if vertex has received any message from other vertices
     *
     * @return true if received any message
     */
    public boolean hasMessagesForNextStep() {
        return !nextStepInbox.isEmpty();
    }

    /**
     *
     * @return Messages received from previous superstep
     */
    public List<MV> getMessages() {
        return this.inbox;
    }
}
