package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.ComputationalVertex;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.Graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

        if(getSuperStep() < 30){

            sendMessageToAllOutNeighbors(vertex, vertex.getComputationalValue() / numOutEdges);

        } else {
            
           vertex.voteToHalt();
        }
    }

    @Override
    protected void masterCompute(List<ComputationalVertex<?, ?, Double, Double>> iterator, HashMap<String, Object> globalValues) {
        //do nothing
    }

    @Override
    protected void initializeGlobalObjects(HashMap<String, Object> globalObjects) {
        //do nothing
    }

    public int getHighestRankNodeID(){

        int index = 0;
        double maxVal = -1;
        List<Double> vertexComputationalValues = getVertexComputationalValues();

        for (int i = 0; i < vertexComputationalValues.size() ; i++) {

            if(vertexComputationalValues.get(i) > maxVal){
                maxVal = vertexComputationalValues.get(i);
                index = i;
            }
        }

        return index;
    }
}
