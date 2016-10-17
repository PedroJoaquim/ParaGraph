package pt.ist.rc.paragraph.computation;

import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.model.Vertex;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Pedro Joaquim.
 */
public abstract class VertexCentricComputation<VV, EV, MV, VCV> {

    private VCV[] vertexComputationalValue;

    private InboxMessages<MV> inboxMessages;

    private GraphData<VV, EV> graphData;

    private ComputationConfig config;

    private boolean[] haltedVertices;

    private int numVertices;

    public VertexCentricComputation(final GraphData<VV, EV> graphData, ComputationConfig config) {

        this.graphData = graphData;
        this.config = config;
        this.numVertices = graphData.getVertices().length;

        //initialize halted vertices
        Thread haltInitThread = new Thread() {
            public void run() {
                haltedVertices = new boolean[numVertices];
                Arrays.fill(haltedVertices, false);
            }
        };

        haltInitThread.start();

        //initialize computations values
        Thread valuesInitThread =  new Thread() {
            public void run() {
                vertexComputationalValue =  (VCV[]) new Object[numVertices]; //small hack

                for (int i = 0; i < numVertices - 1; i++) {
                    vertexComputationalValue[i] = initializeValue(graphData.getVertex(i));
                }
            }
        };

        valuesInitThread.start();

        try {
            haltInitThread.join();
            valuesInitThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract VCV initializeValue(Vertex<VV, EV> vertex);

    public abstract void compute(Vertex<VV, EV> vertex, List<Message<MV>> messages);

    /*
     * Computation Available Functions
     */

    protected int getNumVertices(){
        return this.numVertices;
    }

    protected int getTotalNumberNeighbors(int vertexID){
        //TODO
        return 0;
    }

    protected void voteToHalt(int vertexID){
        this.haltedVertices[vertexID] = true;
    }

    protected void sendMessageToAllOutNeighbors(int vertexID, Message<MV> msg){
        // TODO
    }

    protected void sendMessageTo(int vertexID, int targetID, Message<MV> msg){
        // TODO
    }

    protected void setVertexComputationalValue(int vertexID, VCV value){
        // TODO
    }

    protected VCV getVertexComputationalValue(int vertexID){
        return vertexComputationalValue[vertexID];
    }

    public void execute(){

        //TODO

    }
}
