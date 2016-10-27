package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.ComputationalVertex;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;

import java.util.Iterator;

/**
 * Created by Pedro Joaquim on 17-10-2016
 */
public class ShortestPathVertexComputation extends VertexCentricComputation<Object, Integer, Integer, Integer> {

    private int sourceVertexID;
    public static final int INF = -1;

    public ShortestPathVertexComputation(GraphData<?, Integer> graphData, ComputationConfig config, int sourceVertexID) {
        super(graphData, config);
        this.sourceVertexID = sourceVertexID;
    }

    @Override
    public Integer initializeValue(int vertexID) {
        return -1;
    }

    @Override
    public void compute(ComputationalVertex<?, ? extends Integer, Integer, Integer> vertex) {

        int mindist = vertex.getId() == sourceVertexID ? 0 : INF;

        for (Integer msg : vertex.getMessages()) {
            mindist = shortestDistance(mindist, msg);
        }

        if(isLesserThan(mindist, vertex.getComputationalValue()) && mindist != INF){

            vertex.setComputationalValue(mindist);

            Iterator<? extends Edge<? extends Integer>> iterator = vertex.getOutEdgesIterator();

            while (iterator.hasNext()){
                Edge<? extends Integer> edge = iterator.next();
                sendMessageTo(edge.getTarget(), mindist + edge.getValue());
            }
        }

        vertex.voteToHalt();
    }

    private int shortestDistance(int mindist, int msg) {
        if(mindist == INF) return msg;
        else {
            return mindist < msg ? mindist : msg;
        }
    }

    private boolean isLesserThan(int a, int b) {
        return a != INF && (b == INF || a < b);
    }
}
