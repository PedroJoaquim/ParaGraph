package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.ComputationalVertex;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.Graph;

/**
 * Created by Pedro Joaquim on 17-10-2016
 */
public class PageRankVertexComputation extends VertexCentricComputation<Object, Object, Double, Double> {

    private int superstepNumber;

    public PageRankVertexComputation(Graph<?, ?> graph, ComputationConfig config, int supserstepNumber) {
        super(graph, config);

        this.superstepNumber = supserstepNumber;
    }

    @Override
    protected Double initializeValue(int vertexID) {
        return 1.0 / getNumVertices();
    }

    @Override
    protected void compute(ComputationalVertex<?, ?, Double, Double> vertex) {

        int numOutEdges = vertex.getNumberOutEdges();

        if(getSuperStep() > 0){

            double sum = 0;

            for (Double msg : vertex.getMessages()) {
                sum += msg;
            }

            vertex.setComputationalValue((0.15 / getNumVertices()) + 0.85 * sum);
        }

        if(getSuperStep() < superstepNumber){

            sendMessageToAllOutNeighbors(vertex, vertex.getComputationalValue() / numOutEdges);

        } else {
            
           vertex.voteToHalt();
        }
    }
}
