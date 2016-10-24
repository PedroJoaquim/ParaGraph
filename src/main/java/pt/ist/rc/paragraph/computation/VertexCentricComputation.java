package pt.ist.rc.paragraph.computation;

import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.model.Vertex;

import java.util.*;

/**
 * Created by Pedro Joaquim.
 */
public abstract class VertexCentricComputation<VV, EV, VCV, MV> {

    private List<ComputationalVertex<VV, EV, VCV, MV>> computationalVertices;

    private GraphData<VV, EV> graphData;

    private ComputationConfig config;

    private int numVertices;

    private int superstep;

    private HashSet<Integer> activeVertices;

    public VertexCentricComputation(final GraphData<VV, EV> graphData, ComputationConfig config) {

        this.graphData = graphData;
        this.config = config;
        this.numVertices = graphData.getVertices().length;
        this.activeVertices = new HashSet<>();
        this.superstep = 0;

        computationalVertices =  new ArrayList<>(this.numVertices);

        for (int i = 0; i < numVertices; i++) {
            this.activeVertices.add(i);
            this.computationalVertices.add(i, new ComputationalVertex<>(i, initializeValue(i), graphData.getVertex(i), activeVertices));
        }
    }

    public abstract VCV initializeValue(int vertexID);

    public abstract void compute(ComputationalVertex<VV, EV, VCV, MV> vertex);

    /*
     * Computation Available Functions
     */

    protected int getNumVertices(){
        return this.numVertices;
    }

    protected void sendMessageTo(int targetID, MV msg){
        this.computationalVertices.get(targetID).addNextStepMessage(msg);
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
        for (ComputationalVertex<VV, EV, VCV, MV> computationalVertex : computationalVertices) {
            computationalVertex.swapInboxes();
        }
    }

    private void initializeWorkers(ParaGraphWorker[] workers) {

        for (int i = 0; i < config.getNumWorkers(); i++) {
            int[] assignedPartition = assignPartitionToWorker(i);

            workers[i] = new ParaGraphWorker(assignedPartition[0], assignedPartition[1] ,this);
        }
    }

    protected int[] assignPartitionToWorker(int workerID) {

        int[] result = new int[2];
        int numWorkers = config.getNumWorkers();

        if(graphData.getVertices().length < numWorkers){
            numWorkers = graphData.getVertices().length;
        }

        if(workerID >= numWorkers){

            result[0] = -1;
            result[1] = -1;

        } else {

            int partitionSize = graphData.getVertices().length / numWorkers;

            result[0] = workerID * partitionSize;

            if(workerID == numWorkers -1){

                result[1] = graphData.getVertices().length;
            } else {
                result[1] = result[0] + partitionSize;
            }
        }

        return result;
    }

    private void activateVerticesThatReceivedMessages(){

        for (ComputationalVertex<VV, EV, VCV, MV> computationalVertex : computationalVertices) {
            if(computationalVertex.hasMessagesForNextStep()){
                this.activeVertices.add(computationalVertex.getId());
            }
        }
    }

    public VCV[] getVertexComputationalValues(){

        VCV[] result = (VCV[]) new Object[getNumVertices()];

        for (int i = 0; i < result.length; i++) {
            result[i] = computationalVertices.get(i).getComputationalValue();
        }

        return result;
    }


    public void workerCompute(int i) {
        compute(computationalVertices.get(i));
    }
}
