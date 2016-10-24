package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.ComputationalVertex;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;

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
    public void compute(ComputationalVertex<Void, Void, Double, Double> vertex) {

        int numOutEdges = vertex.getNumberOutEdges();

        if(getSuperStep() > 0){

            double sum = 0;

            for (Double msg : vertex.getMessages()) {
                sum += msg;
            }

            vertex.setComputationalValue((0.15 / getNumVertices()) + 0.85 * sum);
        }

        if(getSuperStep() < 30){

            sendMessageToAllOutNeighbors(vertex, vertex.getComputationalValue() / numOutEdges);

        } else {
            
           vertex.voteToHalt();
        }
    }
}
