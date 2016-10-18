package pt.ist.rc.paragraph.computation;

import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.model.Vertex;

import java.util.*;

/**
 * Created by Pedro Joaquim.
 */
public abstract class VertexCentricComputation<VV, EV, MV, VCV> {

    private VCV[] vertexComputationalValue;

    private InboxMessages<MV> inboxMessages;

    private InboxMessages<MV> nextStepInboxMessages;

    private GraphData<VV, EV> graphData;

    private ComputationConfig config;

    private int numVertices;

    private int superstep;

    private HashSet<Vertex<VV, EV>> activeVertices;

    public VertexCentricComputation(final GraphData<VV, EV> graphData, ComputationConfig config) {

        this.graphData = graphData;
        this.config = config;
        this.numVertices = graphData.getVertices().length;
        this.activeVertices = new HashSet<>(Arrays.asList(graphData.getVertices()));
        this.superstep = 0;
        this.inboxMessages = new InboxMessages<>();
        this.nextStepInboxMessages = new InboxMessages<>();

        vertexComputationalValue =  (VCV[]) new Object[numVertices]; //small hack

        for (int i = 0; i < numVertices - 1; i++) {
            vertexComputationalValue[i] = initializeValue(i);
        }
    }

    public abstract VCV initializeValue(int vertexID);

    public abstract void compute(int vertexID, List<MV> messages);

    public void workerCompute(int vertexID){
        List<MV> messages = inboxMessages.getMessages(vertexID);
        compute(vertexID, messages);
    }

    /*
     * Computation Available Functions
     */

    protected int getNumVertices(){
        return this.numVertices;
    }


    protected void voteToHalt(int vertexID){
        this.activeVertices.remove(graphData.getVertex(vertexID));
    }

    protected Edge<EV>[] getOutEdges(int vertexID){
        return graphData.getVertex(vertexID).getOutEdges();
    }

    protected void sendMessageToAllOutNeighbors(int vertexID, MV msg){
        for (Edge<EV> e: graphData.getVertex(vertexID).getOutEdges()){
            nextStepInboxMessages.addMessageTo(e.getTarget(), msg);
        }
    }

    protected void sendMessageTo(int targetID, MV msg){
        nextStepInboxMessages.addMessageTo(targetID, msg);
    }

    protected void setValue(int vertexID, VCV value){
       vertexComputationalValue[vertexID] = value;
    }

    protected VCV getValue(int vertexID){
        return vertexComputationalValue[vertexID];
    }

    protected VV getVertexProperty(int vertexID){
        return graphData.getVertex(vertexID).getValue();
    }

    protected int getSuperstep(){
        return superstep;
    }

    public void execute() throws InterruptedException {

        ParaGraphWorker[] workers = new ParaGraphWorker[config.getNumWorkers()];
        initializeWorkers(workers);

        while(!activeVertices.isEmpty()){

            for (ParaGraphWorker worker : workers) {
                worker.run();
            }

            for (ParaGraphWorker worker : workers) {
                worker.await();
            }

            activateVerticesThatReceivedMessages();
            exchangeMessagesInboxes();
            superstep++;
        }
    }

    private void exchangeMessagesInboxes() {

        this.inboxMessages.clearInbox();

        InboxMessages<MV> tmp = this.inboxMessages;

        this.inboxMessages = this.nextStepInboxMessages;
        this.nextStepInboxMessages = tmp;
    }

    private void initializeWorkers(ParaGraphWorker[] workers) {

        for (int i = 0; i < config.getNumWorkers(); i++) {
            int[] assignedPartition = assignPartitionToWorker(i);

            workers[i] = new ParaGraphWorker(assignedPartition[0], assignedPartition[1] ,this);
        }
    }

    protected int[] assignPartitionToWorker(int workerID) {

        int[] result = new int[2];

        if(graphData.getVertices().length < config.getNumWorkers()){

            if(workerID > 0){
                result[0] = -1;
                result[1] = -1;
            } else {

                result[0] = 0;
                result[1] = graphData.getVertices().length;
            }
        } else {

            int partitionSize = graphData.getVertices().length / config.getNumWorkers();

            result[0] = workerID * partitionSize;

            if(workerID == config.getNumWorkers() -1){

                result[1] = graphData.getVertices().length;
            } else {
                result[1] = result[0] + partitionSize;
            }
        }

        return result;
    }

    private void activateVerticesThatReceivedMessages(){

        for (int i = 0; i < numVertices; i++) {
            if(!inboxMessages.getMessages(i).isEmpty()){
                this.activeVertices.add(graphData.getVertex(i));
            }
        }
    }
}
