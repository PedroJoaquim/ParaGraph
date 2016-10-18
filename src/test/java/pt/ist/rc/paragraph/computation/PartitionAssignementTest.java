package pt.ist.rc.paragraph.computation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.ist.rc.paragraph.analytics.ConnectedComponentsComputation;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.toygraphs.ToyGraph1;

import static org.junit.Assert.*;

/**
 * Created by Pedro Joaquim.
 */
public class PartitionAssignementTest {

    private static final int NUM_WORKERS = 4;

    private static GraphData<Void, Void> graph1;

    @BeforeClass
    public static void oneTimeInit() {
        graph1 = ToyGraph1.loadGraph();
    }

    @Test
    public void testAssignPartitions1() throws InterruptedException {

        ConnectedComponentsComputation ccc = new ConnectedComponentsComputation(graph1, new ComputationConfig().setNumWorkers(NUM_WORKERS));

        int[] partition1 = ccc.assignPartitionToWorker(0);
        int[] partition2 = ccc.assignPartitionToWorker(1);
        int[] partition3 = ccc.assignPartitionToWorker(2);
        int[] partition4 = ccc.assignPartitionToWorker(3);

        Assert.assertArrayEquals(new int[]{0,2}, partition1);
        Assert.assertArrayEquals(new int[]{2,4}, partition2);
        Assert.assertArrayEquals(new int[]{4,6}, partition3);
        Assert.assertArrayEquals(new int[]{6,9}, partition4);
    }



}