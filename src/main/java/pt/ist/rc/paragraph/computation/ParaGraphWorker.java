package pt.ist.rc.paragraph.computation;

/**
 * Worker thread that is assigned a given graph partition
 */
public class ParaGraphWorker  {

    /**
     * Start partition vertex id
     */
    private int from;

    /**
     * End partition vertex id (exclusive)
     */
    private int to;

    /**
     * Vertex Centric Computation Object for computation
     */
    private VertexCentricComputation vertexComputation;

    /**
     * Java thread being used for current superstep execution
     */
    private Thread workerThread;

    /**
     * Constructor
     *
     * @param from Start partition vertex id
     * @param to End partition vertex id (exclusive)
     * @param vertexComputation Vertex Centric Computation Object for computation
     */
    public ParaGraphWorker(int from, int to, VertexCentricComputation vertexComputation) {
        this.from = from;
        this.to = to;
        this.vertexComputation = vertexComputation;
    }

    /**
     * Run method executed by each worker in parallel that performs the computation
     */
    public void run(){

        if(from != -1 && to != -1){
            workerThread = new Thread(){
                public void run(){
                    for (int i = from; i < to; i++) {
                        vertexComputation.workerCompute(i);
                    }
                }
            };

            workerThread.start();
        }

    }

    /**
     * Wait until the worker terminates current computation
     *
     * @throws InterruptedException
     */
    public void await() throws InterruptedException {
        if(from != -1 && to != -1){ workerThread.join(); }
    }

}
