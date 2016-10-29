package pt.ist.rc.paragraph.parser.gml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GMLParser {
    private final StreamTokenizer st;

    public GMLParser(Reader r) {
        this.st = new StreamTokenizer(r);

        this.st.commentChar(GMLTokens.COMMENT_CHAR);
        this.st.ordinaryChar('[');
        this.st.ordinaryChar(']');
    }

    public Map<String, String> parseGraphProperties() throws IOException {
        final Map<String, String> props = new HashMap<>();
        while (hasNext()) {
            final int type = st.ttype;
            final String key = st.sval;
            if (GMLTokens.NODE.equals(key)) {
                return props;
            }
            else if (type == '[' || GMLTokens.GRAPH.equals(key)) {
                continue;
            }
            else {
                final String value = parseValue();
                props.put(key, value);
            }
        }
        throw new IOException(" incomplete");
    }

    public Map<String, String> parseNode() throws IOException {
        return parseElementProperties();
    }

    public Map<String, String> parseEdge() throws IOException {
        return parseElementProperties();
    }

    public boolean hasNode() throws IOException {
        return GMLTokens.NODE.equals(st.sval);
    }

    public boolean hasEdge() throws IOException {
        return GMLTokens.EDGE.equals(st.sval);
    }

    private Map<String, String> parseElementProperties() throws IOException {
        final Map<String, String> props = new HashMap<>();
        while (hasNext()) {
            final int type = st.ttype;
            if (type == ']') {
                hasNext();
                return props;
            }
            else if (type == '[') {
                continue;
            }
            else {
                final String key = st.sval;
                final String value = parseValue();
                props.put(key, value);
            }
        }
        throw new IOException(" incomplete");
    }

    private String parseValue() throws IOException {
        if (hasNext()) {
            final int type = st.ttype;
            if (type == StreamTokenizer.TT_NUMBER) {
                return parseNumber().toString();
            } else {
                return st.sval;
            }
        }
        throw new IOException("value not found");
    }

    private Number parseNumber() throws IOException {
        final Double doubleValue = st.nval;
        if (doubleValue.equals((double) doubleValue.intValue())) {
            return doubleValue.intValue();
        } else {
            return doubleValue.floatValue();
        }
    }

    private boolean hasNext() throws IOException {
        return st.nextToken() != StreamTokenizer.TT_EOF;
    }
}
