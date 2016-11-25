package pt.ist.rc.paragraph.loader.direct;

import pt.ist.rc.paragraph.model.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

public class DirectLoader<VV, EV> {
    private final Reader r;
    private final Function<String, VV> loadVertexDataFunction;
    private final Function<String, EV> loadEdgeDataFunction;

    public DirectLoader(Reader r, Function<String, VV> loadVertexDataFunction, Function<String, EV> loadEdgeDataFunction) {
        this.r = r;
        this.loadVertexDataFunction = loadVertexDataFunction;
        this.loadEdgeDataFunction = loadEdgeDataFunction;
    }

    public DirectLoader(Reader r) {
        this(r, x -> null, x -> null);
    }

    public Graph<VV, EV> load() throws IOException {
        boolean directed = true;
        Graph.BuilderEdges<VV, EV> partialGraph = new Graph.BuilderEdges<>();

        try(BufferedReader br = new BufferedReader(r)) {
            String line = br.readLine();

            for (; line != null && (line.startsWith("#") || line.startsWith("!")); line = br.readLine()) {
                if (line.startsWith("#")) continue;

                if (line.startsWith("!UNDIRECTED")) {
                    directed = false;
                }
            }

            // TODO: improve directed/undirected code and measure performance
            if (directed) {
                for (; line != null; line = br.readLine()) { // TODO: Check what happens when reader is empty
                    final String[] idxs = line.split("\t");

                    int fromIdx = Integer.parseInt(idxs[0]);
                    int toIdx = Integer.parseInt(idxs[1]);

                    partialGraph.addEdge(fromIdx, toIdx);
                }
            }
            else {
                for (; line != null; line = br.readLine()) { // TODO: Check what happens when reader is empty
                    final String[] idxs = line.split("\t");

                    int fromIdx = Integer.parseInt(idxs[0]);
                    int toIdx = Integer.parseInt(idxs[1]);

                    partialGraph.addUndirectedEdge(fromIdx, toIdx);
                }
            }
        }

        return partialGraph.build();
    }
}
