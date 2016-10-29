package pt.ist.rc.paragraph.loader;

import pt.ist.rc.paragraph.model.Graph;
import pt.ist.rc.paragraph.loader.gml.GMLLoader;

import java.io.*;
import java.util.function.Function;

public class GraphLoader<VV, EV> {
    public Graph<VV, EV> fromFile(String filePath, Function<String, VV> loadVertexDataFunction, Function<String, EV> loadEdgeDataFunction) throws IOException {
        try (Reader r = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            return fromReader(r, loadVertexDataFunction, loadEdgeDataFunction);
        }
    }

    public Graph<VV, EV> fromString(String content, Function<String, VV> loadVertexDataFunction, Function<String, EV> loadEdgeDataFunction) throws IOException {
        Reader r = new StringReader(content);
        return fromReader(r, loadVertexDataFunction, loadEdgeDataFunction);
    }

    public Graph<VV, EV> fromReader(Reader r, Function<String, VV> loadVertexDataFunction, Function<String, EV> loadEdgeDataFunction) throws IOException {
        return new GMLLoader<>(r, loadVertexDataFunction, loadEdgeDataFunction).load();
    }
}
