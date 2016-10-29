package pt.ist.rc.paragraph.computation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.ist.rc.paragraph.analytics.SimpleTriangleCountingAlgorithm;
import pt.ist.rc.paragraph.exceptions.ParaGraphComputationException;
import pt.ist.rc.paragraph.model.Graph;
import pt.ist.rc.paragraph.toygraphs.ToyGraph1;
import pt.ist.rc.paragraph.toygraphs.ToyGraph2;
import pt.ist.rc.paragraph.toygraphs.ToyGraph3;

/**
 * Created by Pedro Joaquim on 27-10-2016
 */
public class SimpleTriangleCountingComputationTest {


    private static final int NUM_WORKERS = 1;

    private static Graph<Void, Void> graph3;
    private static Graph<Void, Void> graph1;
    private static Graph<Void, Integer> graph2;


    @BeforeClass
    public static void oneTimeInit() {
        graph3 = ToyGraph3.loadGraph();
        graph2 = ToyGraph2.loadGraph();
        graph1 = ToyGraph1.loadGraph();
    }

    @Test
    public void testSPV() throws ParaGraphComputationException {

        SimpleTriangleCountingAlgorithm stc = new SimpleTriangleCountingAlgorithm(graph3, new ComputationConfig().setNumWorkers(NUM_WORKERS));
        stc.execute();

        Assert.assertEquals(2, stc.getTriangleCount());
    }

    @Test
    public void testSPV2() throws ParaGraphComputationException {

        SimpleTriangleCountingAlgorithm stc = new SimpleTriangleCountingAlgorithm(graph1, new ComputationConfig().setNumWorkers(NUM_WORKERS));
        stc.execute();

        Assert.assertEquals(0, stc.getTriangleCount());
    }

    @Test
    public void testSPV3() throws ParaGraphComputationException {

        SimpleTriangleCountingAlgorithm stc = new SimpleTriangleCountingAlgorithm(graph2, new ComputationConfig().setNumWorkers(NUM_WORKERS));
        stc.execute();

        Assert.assertEquals(0, stc.getTriangleCount());

    }

}