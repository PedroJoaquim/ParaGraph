package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.GraphData;

import java.util.List;

/**
 * Created by Pedro Joaquim on 17-10-2016
 */
public class PageRankVertexComputation extends VertexCentricComputation<Void, Void, Double, Double> {

    public PageRankVertexComputation(GraphData<Void, Void> graphData, ComputationConfig config) {
        super(graphData, config);
    }

    @Override
    public Double initializeValue(int vertexID) {
        return 1.0 / getNumVertices();
    }

    @Override
    public void compute(int vertexID, List<Double> messages) {

        int numOutEdges = getOutEdges(vertexID).length;

        if(getSuperstep() > 0){

            double sum = 0;

            for (Double msg : messages) {
                sum += msg;
            }

            setValue(vertexID, (0.15 / getNumVertices()) + 0.85 * sum);

        }

        if(getSuperstep() < 30){
            sendMessageToAllOutNeighbors(vertexID, getValue(vertexID) / numOutEdges);
        } else {
            voteToHalt(vertexID);
        }
    }
}
