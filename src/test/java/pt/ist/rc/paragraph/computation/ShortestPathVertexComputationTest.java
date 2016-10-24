package pt.ist.rc.paragraph.computation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.ist.rc.paragraph.analytics.ShortestPathVertexComputation;
import pt.ist.rc.paragraph.exceptions.ParaGraphComputationException;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.toygraphs.ToyGraph2;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Pedro Joaquim.
 */
public class ShortestPathVertexComputationTest {

    private static final int NUM_WORKERS = 4;

    private static GraphData<Void, Integer> graph1;


    @BeforeClass
    public static void oneTimeInit() {
        graph1 = ToyGraph2.loadGraph();
    }

    @Test
    public void testSPV() throws ParaGraphComputationException {

        ShortestPathVertexComputation spv = new ShortestPathVertexComputation(graph1, new ComputationConfig().setNumWorkers(NUM_WORKERS), 0);
        spv.execute();


        List<Integer> distances = spv.getVertexComputationalValues();

        Assert.assertEquals(6, spv.getNumVertices());

        Assert.assertEquals(0, distances.get(0).intValue());
        Assert.assertEquals(4, distances.get(1).intValue());
        Assert.assertEquals(9, distances.get(2).intValue());
        Assert.assertEquals(14, distances.get(3).intValue());
        Assert.assertEquals(12, distances.get(4).intValue());
        Assert.assertEquals(25, distances.get(5).intValue());
    }
}
