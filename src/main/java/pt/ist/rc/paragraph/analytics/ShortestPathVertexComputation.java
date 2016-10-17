package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;

import java.util.List;

/**
 * Created by Pedro Joaquim on 17-10-2016
 */
public class ShortestPathVertexComputation extends VertexCentricComputation<Void, Integer, Integer, Integer> {

    private int sourceVertexID;
    private static final int INF = -1;

    public ShortestPathVertexComputation(GraphData<Void, Integer> graphData, ComputationConfig config, int sourceVertexID) {
        super(graphData, config);

        this.sourceVertexID = sourceVertexID;
    }

    @Override
    public Integer initializeValue(int vertexID) {
        return vertexID == sourceVertexID ? 0 : INF;
    }

    @Override
    public void compute(int vertexID, List<Integer> messages) {

        int mindist = getValue(vertexID);

        for (Integer msg : messages) {
            mindist = shortestDistance(mindist, msg);
        }

        if(mindist < getValue(vertexID)){

            setValue(vertexID, mindist);

            for (Edge<Integer> edge: getOutEdges(vertexID)) {
                sendMessageTo(edge.getTarget(), mindist + edge.getValue());
            }
        }

        voteToHalt(vertexID);
    }

    private int shortestDistance(int mindist, int msg) {
        if(mindist == INF) return msg;
        else {
            return mindist < msg ? mindist : msg;
        }
    }
}
