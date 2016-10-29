package pt.ist.rc.paragraph.computation;

import pt.ist.rc.paragraph.exceptions.ParaGraphComputationException;
import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.Graph;

import java.util.*;

/**
 * Main Class that should be extended to implement concrete algorithms
 */
public abstract class VertexCentricComputation<VV, EV, VCV, MV> {

    /**
     * List of computational vertex representing the graph core vertices
     */
    private List<ComputationalVertex<? extends VV,? extends EV, VCV, MV>> computationalVertices;

    /**
     * Current computation configuration
     */
    private ComputationConfig config;

    /**
     * Graph number of vertices
     */
    private int numVertices;

    /**
     * Current Super Step
     */
    private int superStep;

    /**
     * Set of active Vertices
     */
    private HashSet<Integer> activeVertices;

    /**
     * Constructor
     *
     *  ? extends VV and ? extends EV are used in order to allow some algorithms that dont want a specific VV and EV value
     *  to extend VertexCentricComputation<Object, Object, <class for VCV>, <class for MV>> and thid way you can pass any arbitrary
     *  graph data object
     *
     *  for instance you have a graph data object of type GraphData<Integer, Double>
     *
     *      this way you can pass it to a algorithm that extends VertexCentricComputation<Object, Object, Integer, Integer>
     *
     * @param graph  Core graph data
     * @param config Computation configuration
     */
    public VertexCentricComputation(final Graph<? extends VV, ? extends EV> graph, ComputationConfig config) {

        this.config = config;
        this.numVertices = graph.getVertices().size();
        this.activeVertices = new HashSet<>();
        this.superStep = 0;

        computationalVertices =  new ArrayList<>(this.numVertices);

        for (int i = 0; i < numVertices; i++) {
            this.activeVertices.add(i);
            this.computationalVertices.add(i, new ComputationalVertex<>(i, initializeValue(i), graph.getVertex(i), activeVertices));
        }
    }

    /**
     * @param vertexID id of the vertex that will receive the initialization value
     * @return initialization value
     */
    protected abstract VCV initializeValue(int vertexID);

    /**
     *
     * @param vertex that will execute the compute method
     */
    protected abstract void compute(ComputationalVertex<? extends VV, ? extends EV, VCV, MV> vertex);


    /**
     *
     * @return Core graph number of vertices
     */
    protected int getNumVertices(){
        return this.numVertices;
    }

    /**
     *
     * @param targetID target vertex id
     * @param msg message to be sent to the  {@code targetID}
     */
    protected void sendMessageTo(int targetID, MV msg){
        this.computationalVertices.get(targetID).addNextStepMessage(msg);
    }

    /**
     * util function to send message to all vertex out neighbors
     */
    protected void sendMessageToAllOutNeighbors(ComputationalVertex<? extends VV, ? extends EV, VCV, MV> vertex, MV msg){

        Iterator<? extends Edge<? extends EV>> iterator = vertex.getOutEdgesIterator();

        while (iterator.hasNext()){
            Edge<? extends EV> edge = iterator.next();
            sendMessageTo(edge.getTargetIdx(), msg);
        }
    }


    /**
     *
     * @return Current super step number
     */
    protected int getSuperStep(){
        return superStep;
    }

    /**
     * Main BSP execution loop
     *
     * @throws ParaGraphComputationException
     */
    public void execute() throws ParaGraphComputationException {

        try{
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
                superStep++;
            }
        } catch (Exception e) {
            throw new ParaGraphComputationException(e.getMessage());
        }

    }

    /**
     * Prepare vertices inbox messages for next super step
     */
    private void exchangeMessagesInboxes() {
        for (ComputationalVertex<? extends VV, ? extends EV, VCV, MV> computationalVertex : computationalVertices) {
            computationalVertex.swapInboxes();
        }
    }

    /**
     * Method that initiates worker instances
     *
     * @param workers worker instances to be initialized
     */
    private void initializeWorkers(ParaGraphWorker[] workers) {

        for (int i = 0; i < config.getNumWorkers(); i++) {
            int[] assignedPartition = assignPartitionToWorker(i);

            workers[i] = new ParaGraphWorker(assignedPartition[0], assignedPartition[1] ,this);
        }
    }

    /**
     * Assign partition to worker
     * @param workerID Worker ID
     * @return array with start and end partion limits
     */
    protected int[] assignPartitionToWorker(int workerID) {

        int[] result = new int[2];
        int numWorkers = config.getNumWorkers();

        if(this.numVertices < numWorkers){
            numWorkers = this.numVertices;
        }

        if(workerID >= numWorkers){

            result[0] = -1;
            result[1] = -1;

        } else {

            int partitionSize = this.numVertices / numWorkers;

            result[0] = workerID * partitionSize;

            if(workerID == numWorkers -1){

                result[1] = this.numVertices;
            } else {
                result[1] = result[0] + partitionSize;
            }
        }

        return result;
    }

    /**
     * Activate vertices that voted to halt but received messages from other vertices
     */
    private void activateVerticesThatReceivedMessages(){

        for (ComputationalVertex<? extends VV, ? extends EV, VCV, MV> computationalVertex : computationalVertices) {
            if(computationalVertex.hasMessagesForNextStep()){
                this.activeVertices.add(computationalVertex.getId());
            }
        }
    }

    /**
     *
     * @return List of computational values associated to each vertex
     */
    public List<VCV> getVertexComputationalValues(){

        List<VCV> result = new ArrayList<>(this.numVertices);

        for (int i = 0; i < this.numVertices; i++) {
            result.add(i, computationalVertices.get(i).getComputationalValue());
        }

        return result;
    }

    /**
     *
     * @param i vertex id that should be executed
     */
    protected void workerCompute(int i) {
        compute(computationalVertices.get(i));
    }

}
