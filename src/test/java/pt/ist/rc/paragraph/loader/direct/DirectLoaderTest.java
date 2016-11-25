package pt.ist.rc.paragraph.loader.direct;

import org.junit.Assert;
import org.junit.Test;
import pt.ist.rc.paragraph.model.Graph;
import pt.ist.rc.paragraph.model.Vertex;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectLoaderTest {
    private static final String DIRECT_BASIC = "# This is just a comment\n" +
            "0\t1\n" +
            "0\t2\n" +
            "1\t0\n" +
            "2\t1\n";

    private static final String DIRECT_FAN =
            "2\t0\n" +
            "2\t1\n" +
            "2\t3\n" +
            "2\t4\n";

    private static final String UNDIRECTED =
            "!UNDIRECTED\n" +
            "0\t3\n" +
            "1\t2\n";

    @Test
    public void importBasicDirect() throws IOException {
        Reader directReader = new StringReader(DIRECT_BASIC);
        DirectLoader<Void, Void> loader = new DirectLoader<>(directReader);

        Graph<Void, Void> graph = loader.load();

        Vertex<Void, Void> vertex0 = graph.getVertex(0);
        Vertex<Void, Void> vertex1 = graph.getVertex(1);
        Vertex<Void, Void> vertex2 = graph.getVertex(2);

        final Set<Integer> vertex0OutIdxs = vertex0.getOutEdges().stream().map(edge -> edge.getTargetIdx()).collect(Collectors.toSet());
        final Set<Integer> vertex1OutIdxs = vertex1.getOutEdges().stream().map(edge -> edge.getTargetIdx()).collect(Collectors.toSet());
        final Set<Integer> vertex2OutIdxs = vertex2.getOutEdges().stream().map(edge -> edge.getTargetIdx()).collect(Collectors.toSet());

        Assert.assertTrue(vertex0OutIdxs.contains(1));
        Assert.assertTrue(vertex0OutIdxs.contains(2));
        Assert.assertFalse(vertex0OutIdxs.contains(0));

        Assert.assertTrue(vertex1OutIdxs.contains(0));
        Assert.assertFalse(vertex1OutIdxs.contains(1));
        Assert.assertFalse(vertex1OutIdxs.contains(2));

        Assert.assertTrue(vertex2OutIdxs.contains(1));
        Assert.assertFalse(vertex2OutIdxs.contains(0));
        Assert.assertFalse(vertex2OutIdxs.contains(2));
    }

    @Test
    public void importDirectFan() throws IOException {
        Reader directReader = new StringReader(DIRECT_FAN);
        DirectLoader<Void, Void> loader = new DirectLoader<>(directReader);

        Graph<Void, Void> graph = loader.load();

        Vertex<Void, Void> vertex0 = graph.getVertex(0);
        Vertex<Void, Void> vertex2 = graph.getVertex(2);
        Vertex<Void, Void> vertex4 = graph.getVertex(4);

        final Set<Integer> vertex0OutIdxs = vertex0.getOutEdges().stream().map(edge -> edge.getTargetIdx()).collect(Collectors.toSet());
        final Set<Integer> vertex2OutIdxs = vertex2.getOutEdges().stream().map(edge -> edge.getTargetIdx()).collect(Collectors.toSet());
        final Set<Integer> vertex4OutIdxs = vertex4.getOutEdges().stream().map(edge -> edge.getTargetIdx()).collect(Collectors.toSet());

        Assert.assertTrue(vertex0OutIdxs.isEmpty());
        Assert.assertTrue(vertex4OutIdxs.isEmpty());

        Assert.assertTrue(vertex2OutIdxs.contains(0));
        Assert.assertTrue(vertex2OutIdxs.contains(1));
        Assert.assertTrue(vertex2OutIdxs.contains(3));
        Assert.assertTrue(vertex2OutIdxs.contains(4));
        Assert.assertFalse(vertex2OutIdxs.contains(2));
    }

    @Test
    public void importUndirected() throws IOException {
        Reader undirectedReader = new StringReader(UNDIRECTED);
        DirectLoader<Void, Void> loader = new DirectLoader<>(undirectedReader);

        Graph<Void, Void> graph = loader.load();

        Vertex<Void, Void> vertex0 = graph.getVertex(0);
        Vertex<Void, Void> vertex1 = graph.getVertex(1);
        Vertex<Void, Void> vertex2 = graph.getVertex(2);
        Vertex<Void, Void> vertex3 = graph.getVertex(3);

        final Set<Integer> vertex0OutIdxs = vertex0.getOutEdges().stream().map(edge -> edge.getTargetIdx()).collect(Collectors.toSet());
        final Set<Integer> vertex1OutIdxs = vertex1.getOutEdges().stream().map(edge -> edge.getTargetIdx()).collect(Collectors.toSet());
        final Set<Integer> vertex2OutIdxs = vertex2.getOutEdges().stream().map(edge -> edge.getTargetIdx()).collect(Collectors.toSet());
        final Set<Integer> vertex3OutIdxs = vertex3.getOutEdges().stream().map(edge -> edge.getTargetIdx()).collect(Collectors.toSet());

        Assert.assertTrue(vertex0OutIdxs.contains(3));
        Assert.assertTrue(vertex3OutIdxs.contains(0));

        Assert.assertTrue(vertex1OutIdxs.contains(2));
        Assert.assertTrue(vertex2OutIdxs.contains(1));
    }
}
