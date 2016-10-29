package pt.ist.rc.paragraph.computation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.ist.rc.paragraph.analytics.ConnectedComponentsComputation;
import pt.ist.rc.paragraph.analytics.ShortestPathVertexComputation;
import pt.ist.rc.paragraph.model.Graph;
import pt.ist.rc.paragraph.toygraphs.ToyGraph1;
import pt.ist.rc.paragraph.toygraphs.ToyGraph2;

/**
 * Created by Pedro Joaquim.
 */
public class PartitionAssignementTest {

    private static Graph<Void, Void> graph1;
    private static Graph<Void, Integer> graph2;

    @BeforeClass
    public static void oneTimeInit() {
        graph1 = ToyGraph1.loadGraph();
        graph2 = ToyGraph2.loadGraph();
    }

    @Test
    public void testAssignPartitions1() throws InterruptedException {

        ConnectedComponentsComputation ccc = new ConnectedComponentsComputation(graph1, new ComputationConfig().setNumWorkers(4));

        int[] partition1 = ccc.assignPartitionToWorker(0);
        int[] partition2 = ccc.assignPartitionToWorker(1);
        int[] partition3 = ccc.assignPartitionToWorker(2);
        int[] partition4 = ccc.assignPartitionToWorker(3);

        Assert.assertArrayEquals(new int[]{0,2}, partition1);
        Assert.assertArrayEquals(new int[]{2,4}, partition2);
        Assert.assertArrayEquals(new int[]{4,6}, partition3);
        Assert.assertArrayEquals(new int[]{6,9}, partition4);
    }


    @Test
    public void testAssignPartitions2() throws InterruptedException {

        ConnectedComponentsComputation ccc = new ConnectedComponentsComputation(graph1, new ComputationConfig().setNumWorkers(1));

        int[] partition1 = ccc.assignPartitionToWorker(0);

        Assert.assertArrayEquals(new int[]{0, 9}, partition1);
    }

    @Test
    public void testAssignPartitions3() throws InterruptedException {

        ConnectedComponentsComputation ccc = new ConnectedComponentsComputation(graph1, new ComputationConfig().setNumWorkers(15));

        int[] partition1 = ccc.assignPartitionToWorker(0);
        int[] partition2 = ccc.assignPartitionToWorker(1);
        int[] partition3 = ccc.assignPartitionToWorker(2);
        int[] partition4 = ccc.assignPartitionToWorker(3);
        int[] partition5 = ccc.assignPartitionToWorker(4);
        int[] partition6 = ccc.assignPartitionToWorker(5);
        int[] partition7 = ccc.assignPartitionToWorker(6);
        int[] partition8 = ccc.assignPartitionToWorker(7);
        int[] partition9 = ccc.assignPartitionToWorker(8);
        int[] partition10 = ccc.assignPartitionToWorker(9);
        int[] partition11 = ccc.assignPartitionToWorker(10);
        int[] partition12 = ccc.assignPartitionToWorker(11);
        int[] partition13 = ccc.assignPartitionToWorker(12);
        int[] partition14 = ccc.assignPartitionToWorker(13);
        int[] partition15 = ccc.assignPartitionToWorker(14);

        Assert.assertArrayEquals(new int[]{0, 1}, partition1);
        Assert.assertArrayEquals(new int[]{1, 2}, partition2);
        Assert.assertArrayEquals(new int[]{2, 3}, partition3);
        Assert.assertArrayEquals(new int[]{3, 4}, partition4);
        Assert.assertArrayEquals(new int[]{4, 5}, partition5);
        Assert.assertArrayEquals(new int[]{5, 6}, partition6);
        Assert.assertArrayEquals(new int[]{6, 7}, partition7);
        Assert.assertArrayEquals(new int[]{7, 8}, partition8);
        Assert.assertArrayEquals(new int[]{8, 9}, partition9);
        Assert.assertArrayEquals(new int[]{-1, -1}, partition10);
        Assert.assertArrayEquals(new int[]{-1, -1}, partition11);
        Assert.assertArrayEquals(new int[]{-1, -1}, partition12);
        Assert.assertArrayEquals(new int[]{-1, -1}, partition13);
        Assert.assertArrayEquals(new int[]{-1, -1}, partition14);
        Assert.assertArrayEquals(new int[]{-1, -1}, partition15);
    }

    @Test
    public void testAssignPartitions4() throws InterruptedException {

        ShortestPathVertexComputation ccc = new ShortestPathVertexComputation(graph2, new ComputationConfig().setNumWorkers(6), 1);

        int[] partition1 = ccc.assignPartitionToWorker(0);
        int[] partition2 = ccc.assignPartitionToWorker(1);
        int[] partition3 = ccc.assignPartitionToWorker(2);
        int[] partition4 = ccc.assignPartitionToWorker(3);
        int[] partition5 = ccc.assignPartitionToWorker(4);
        int[] partition6 = ccc.assignPartitionToWorker(5);

        Assert.assertArrayEquals(new int[]{0, 1}, partition1);
        Assert.assertArrayEquals(new int[]{1, 2}, partition2);
        Assert.assertArrayEquals(new int[]{2, 3}, partition3);
        Assert.assertArrayEquals(new int[]{3, 4}, partition4);
        Assert.assertArrayEquals(new int[]{4, 5}, partition5);
        Assert.assertArrayEquals(new int[]{5, 6}, partition6);
    }





}