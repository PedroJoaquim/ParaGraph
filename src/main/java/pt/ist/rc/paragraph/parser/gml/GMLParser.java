package pt.ist.rc.paragraph.parser.gml;

import java.io.*;

public class GMLParser {
    public static StreamTokenizer readFile(String filePath) throws IOException {
        try (final Reader r = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            final StreamTokenizer st = new StreamTokenizer(r);

            st.commentChar(GMLTokens.COMMENT_CHAR);
            st.ordinaryChar('[');
            st.ordinaryChar(']');

            return st;
        }
    }
}
