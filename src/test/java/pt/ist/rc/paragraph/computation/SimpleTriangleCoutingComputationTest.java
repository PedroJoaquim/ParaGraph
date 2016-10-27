package pt.ist.rc.paragraph.computation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.ist.rc.paragraph.analytics.ShortestPathVertexComputation;
import pt.ist.rc.paragraph.analytics.SimpleTriangleCountingAlgortihm;
import pt.ist.rc.paragraph.exceptions.ParaGraphComputationException;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.toygraphs.ToyGraph1;
import pt.ist.rc.paragraph.toygraphs.ToyGraph2;
import pt.ist.rc.paragraph.toygraphs.ToyGraph3;

import java.util.List;

/**
 * Created by Pedro Joaquim on 27-10-2016
 */
public class SimpleTriangleCoutingComputationTest {


    private static final int NUM_WORKERS = 1;

    private static GraphData<Void, Void> graph3;
    private static GraphData<Void, Void> graph1;
    private static GraphData<Void, Integer> graph2;


    @BeforeClass
    public static void oneTimeInit() {
        graph3 = ToyGraph3.loadGraph();
        graph2 = ToyGraph2.loadGraph();
        graph1 = ToyGraph1.loadGraph();
    }

    @Test
    public void testSPV() throws ParaGraphComputationException {

        SimpleTriangleCountingAlgortihm stc = new SimpleTriangleCountingAlgortihm(graph3, new ComputationConfig().setNumWorkers(NUM_WORKERS));
        stc.execute();

        Assert.assertEquals(2, stc.getTriangleCount());
    }

    @Test
    public void testSPV2() throws ParaGraphComputationException {

        SimpleTriangleCountingAlgortihm stc = new SimpleTriangleCountingAlgortihm(graph1, new ComputationConfig().setNumWorkers(NUM_WORKERS));
        stc.execute();

        Assert.assertEquals(0, stc.getTriangleCount());
    }

    @Test
    public void testSPV3() throws ParaGraphComputationException {

        SimpleTriangleCountingAlgortihm stc = new SimpleTriangleCountingAlgortihm(graph2, new ComputationConfig().setNumWorkers(NUM_WORKERS));
        stc.execute();

        Assert.assertEquals(0, stc.getTriangleCount());

    }

}
