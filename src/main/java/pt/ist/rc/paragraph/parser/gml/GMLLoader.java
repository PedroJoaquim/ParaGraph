package pt.ist.rc.paragraph.parser.gml;

import pt.ist.rc.paragraph.model.DuplicateVertexIdException;
import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.Graph;
import pt.ist.rc.paragraph.model.Vertex;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.function.Function;

public class GMLLoader<VV, EV> {
    private final GMLParser gml;
    private final Function<String, VV> loadVertexDataFunction;
    private final Function<String, EV> loadEdgeDataFunction;

    public GMLLoader(Reader r, Function<String, VV> loadVertexDataFunction, Function<String, EV> loadEdgeDataFunction) {
        gml = new GMLParser(r);
        this.loadVertexDataFunction = loadVertexDataFunction;
        this.loadEdgeDataFunction = loadEdgeDataFunction;
    }

    public Graph<VV, EV> load() throws IOException {
        Graph.BuilderWithIds<VV, EV> partialGraph = loadGraphProperties(gml.parseGraphProperties());

        while (gml.hasNode()) {
            Map<String, String> nodeProps = gml.parseNode();
            Vertex.Builder<VV, EV> node = loadNode(nodeProps);

            try {
                partialGraph.addVertex(node);
            } catch (DuplicateVertexIdException e) {
                throw new IOException("The 'id' property of nodes should be unique.");
            }
        }

        while (gml.hasEdge()) {
            Map<String, String> edgeProps = gml.parseEdge();
            Edge.Builder<EV> partialEdge = loadEdge(edgeProps);

            partialGraph.addEdge(partialEdge);
        }

        return partialGraph.build();
    }

    private Graph.BuilderWithIds<VV, EV> loadGraphProperties(Map<String, String> props) {
        Graph.BuilderWithIds<VV, EV> partialGraph = new Graph.BuilderWithIds<>();

        if (props.containsKey("directed")) {
            boolean directed = "1".equals(props.get("directed"));
            partialGraph.directed(directed);
        }

        if (props.containsKey("comment")) {
            partialGraph.comment(props.get("comment"));
        }

        return partialGraph;
    }

    private Vertex.Builder<VV, EV> loadNode(Map<String, String> props) {
        Vertex.Builder<VV, EV> partialNode = new Vertex.Builder<>();

        if (props.containsKey("value")) {
            partialNode.value(loadVertexDataFunction.apply(props.get("value")));
        }

        if (props.containsKey("id")) {
            partialNode.id(props.get("id"));
        }

        if (props.containsKey("label")) {
            partialNode.label(props.get("label"));
        }

        return partialNode;
    }

    private Edge.Builder<EV> loadEdge(Map<String, String> props) throws IOException {
        validatePropsKey(props, "source", "Edge");
        validatePropsKey(props, "target", "Edge");

        Edge.Builder<EV> partialEdge = new Edge.Builder<EV>()
                .source(props.get("source"))
                .target(props.get("target"));

        if (props.containsKey("value")) {
            partialEdge.value(loadEdgeDataFunction.apply(props.get("value")));
        }

        if (props.containsKey("label")) {
            partialEdge.label(props.get("label"));
        }

        return partialEdge;
    }

    private void validatePropsKey(final Map<String, String> props, final String key, final String elementName) throws IOException {
        if (!props.containsKey(key)) {
            throw new IOException("Property '" + key + "' is required on " + elementName + " elements.");
        }
    }
}
