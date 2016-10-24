package pt.ist.rc.paragraph.computation;

import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.model.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Pedro Joaquim.
 */
public class ComputationalVertex<VV, EV, VCV, MV> {

    private int id;

    private VCV computationalValue;

    private Vertex<VV, EV> coreVertex;

    private List<MV> inbox;

    private List<MV> nextStepInbox;

    private final HashSet<Integer> activeVertices;

    public ComputationalVertex(int id, VCV computationalValue, Vertex<VV, EV> coreVertex, HashSet<Integer> activeVertices) {
        this.id = id;
        this.computationalValue = computationalValue;
        this.coreVertex = coreVertex;
        this.inbox = new ArrayList<MV>();
        this.nextStepInbox = new ArrayList<MV>();
        this.activeVertices = activeVertices;
    }

    public int getId() {
        return id;
    }

    public Edge<EV>[] getOutEdges(){
        return coreVertex.getOutEdges();
    }

    public VCV getComputationalValue() {
        return computationalValue;
    }

    public void setComputationalValue(VCV computationalValue) {
        this.computationalValue = computationalValue;
    }

    public synchronized void addNextStepMessage(MV msg){
        this.nextStepInbox.add(msg);
    }

    public void swapInboxes(){
        this.inbox = nextStepInbox;
        this.nextStepInbox = new ArrayList<MV>();
    }

    public void voteToHalt(){
        synchronized (activeVertices){
            activeVertices.remove(this.id);
        }
    }

    public boolean hasMessagesForNextStep() {
        return !nextStepInbox.isEmpty();
    }

    public List<MV> getMessages() {
        return this.inbox;
    }
}
