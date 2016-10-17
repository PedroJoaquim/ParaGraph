package pt.ist.rc.paragraph.computation;

import pt.ist.rc.paragraph.model.Vertex;

import java.util.List;

/**
 * Created by Pedro Joaquim on 17-10-2016
 */
public class ParaGraphWorker  {

    private int from;

    private int to;

    private VertexCentricComputation vertexComputation;

    private Thread workerThread;

    public ParaGraphWorker(int from, int to, VertexCentricComputation vertexComputation) {
        this.from = from;
        this.to = to;
        this.vertexComputation = vertexComputation;
    }

    public void run(){

        workerThread = new Thread(){
            public void run(){
                for (int i = from; i < to; i++) {
                    vertexComputation.workerCompute(i);
                }
            }
        };

        workerThread.start();
    }

    public void await() throws InterruptedException {
        workerThread.join();
    }

}
