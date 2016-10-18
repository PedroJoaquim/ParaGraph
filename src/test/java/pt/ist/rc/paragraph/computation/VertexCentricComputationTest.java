package pt.ist.rc.paragraph.computation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.ist.rc.paragraph.analytics.ConnectedComponentsComputation;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.toygraphs.ToyGraph1;

import static org.junit.Assert.*;

/**
 * Created by Pedro Joaquim.
 */
public class VertexCentricComputationTest {

    private static final int NUM_WORKERS = 1;

    private static GraphData<Void, Void> graph1;

    @BeforeClass
    public static void oneTimeInit() {
        graph1 = ToyGraph1.loadGraph();
    }

    @Test
    public void testCCC() throws InterruptedException {

        ConnectedComponentsComputation ccc = new ConnectedComponentsComputation(graph1, new ComputationConfig().setNumWorkers(NUM_WORKERS));

        ccc.execute();
    }
}