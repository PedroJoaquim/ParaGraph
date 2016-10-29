package pt.ist.rc.paragraph.parser.gml;

import org.junit.Assert;
import org.junit.Test;
import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.Graph;
import pt.ist.rc.paragraph.model.Vertex;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class GMLLoaderTest {
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
    public void importBasicGML() throws IOException {
        Reader gml = new StringReader(GML_BASIC);
        GMLLoader<Void, Void> loader = new GMLLoader<>(gml, x -> null, x -> null);

        Graph<Void, Void> graph = loader.load();

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
}
