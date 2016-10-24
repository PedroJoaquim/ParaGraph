package pt.ist.rc.paragraph.computation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.ist.rc.paragraph.analytics.ConnectedComponentsComputation;
import pt.ist.rc.paragraph.exceptions.ParaGraphComputationException;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.toygraphs.ToyGraph1;

import java.util.List;
import java.util.Map;

/**
 * Created by Pedro Joaquim.
 */
public class ConnectedComponentsComputationTest {

    private static final int NUM_WORKERS = 4;

    private static GraphData<Void, Void> graph1;

    @BeforeClass
    public static void oneTimeInit() {
        graph1 = ToyGraph1.loadGraph();
    }

    @Test
    public void testCCC() throws ParaGraphComputationException {

        ConnectedComponentsComputation ccc = new ConnectedComponentsComputation(graph1, new ComputationConfig().setNumWorkers(NUM_WORKERS));

        ccc.execute();

        Map<Integer, List<Integer>> verticesGroups = ccc.getVerticesGroups();

        Assert.assertEquals(3, verticesGroups.keySet().size());
        Assert.assertTrue(verticesGroups.keySet().contains(0));
        Assert.assertTrue(verticesGroups.keySet().contains(4));
        Assert.assertTrue(verticesGroups.keySet().contains(7));

        for (Map.Entry<Integer, List<Integer>> entry : ccc.getVerticesGroups().entrySet()) {

            if (entry.getKey() == 0) {

                Assert.assertTrue(entry.getValue().contains(0));
                Assert.assertTrue(entry.getValue().contains(1));
                Assert.assertTrue(entry.getValue().contains(2));
                Assert.assertTrue(entry.getValue().contains(3));

            } else if (entry.getKey() == 4) {

                Assert.assertTrue(entry.getValue().contains(4));
                Assert.assertTrue(entry.getValue().contains(5));
                Assert.assertTrue(entry.getValue().contains(6));

            } else {

                Assert.assertTrue(entry.getValue().contains(7));
                Assert.assertTrue(entry.getValue().contains(8));
            }
        }

    }
}