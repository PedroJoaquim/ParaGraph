package pt.ist.rc.paragraph.loader;

import org.junit.Assert;
import org.junit.Test;
import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.Graph;
import pt.ist.rc.paragraph.model.Vertex;

import java.io.IOException;
import java.util.List;

public class GraphLoaderTest {
    private static final String GML_BASIC = "graph [\n"+
            "  directed 1\n"+
            "  node [\n"+
            "    id A\n"+
            "  ]\n"+
            "  node [\n"+
            "    id B\n"+
            "  ]\n"+
            "  edge [\n"+
            "    source A\n"+
            "    target B\n"+
            "  ]\n"+
            "]";

    @Test
    public void importBasicGMLFromString() throws IOException {
        Graph<Void, Void> graph = new GraphLoader<Void, Void>().fromString(GML_BASIC, x -> null, x -> null, "gml");

        Vertex<Void, Void> vertexA = graph.getVertexById("A").get();
        Vertex<Void, Void> vertexB = graph.getVertexById("B").get();
        List<Edge<Void>> vertexAOutEdges = vertexA.getOutEdges();
        List<Edge<Void>> vertexBOutEdges = vertexB.getOutEdges();
        Edge<Void> edgeAB = vertexAOutEdges.get(0);

        Assert.assertEquals(2, graph.getVertices().size());
        Assert.assertEquals(1, vertexAOutEdges.size());
        Assert.assertTrue(vertexBOutEdges.isEmpty());
        Assert.assertSame(vertexB, graph.getVertices().get(edgeAB.getTargetIdx()));
    }

    @Test
    public void importGMLFromFile() throws IOException {
        Graph<Integer, Void> graph = new GraphLoader<Integer, Void>().fromFile("src/test/resources/people-sample.gml", Integer::parseInt, x -> null, "gml");

        Vertex<Integer, Void> john = graph.getVertexById("879").get();
        Vertex<Integer, Void> frank = graph.getVertexById("465").get();
        Vertex<Integer, Void> pam = graph.getVertexById("321").get();

        Edge<Void> edgePamJohn = pam.getOutEdges().get(0);

        Assert.assertEquals(3, graph.getVertices().size());
        Assert.assertSame(john, graph.getVertices().get(edgePamJohn.getTargetIdx()));
        Assert.assertEquals(34, frank.getValue().intValue());
    }
}
