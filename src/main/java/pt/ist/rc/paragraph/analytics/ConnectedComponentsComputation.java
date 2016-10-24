package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.ComputationalVertex;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;
import pt.ist.rc.paragraph.model.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pedro Joaquim on 17-10-2016
 */
public class ConnectedComponentsComputation extends VertexCentricComputation<Void, Void, Integer, Integer> {

    public ConnectedComponentsComputation(GraphData<Void, Void> graphData, ComputationConfig config) {
        super(graphData, config);
    }

    @Override
    public Integer initializeValue(int vertexID) {
        return vertexID;
    }

    @Override
    public void compute(ComputationalVertex<Void, Void, Integer, Integer> vertex) {

        if(getSuperstep() == 0){

            for (Edge<Void> edge: vertex.getOutEdges()) {
                sendMessageTo(edge.getTarget(), vertex.getId());
            }

        } else {

            int minValue = vertex.getId();

            for (Integer msg : vertex.getMessages()) {
                if (msg < minValue){
                    minValue = msg;
                }
            }

            if(minValue < vertex.getComputationalValue()){

                vertex.setComputationalValue(minValue);

                for (Edge<Void> edge: vertex.getOutEdges()) {
                    sendMessageTo(edge.getTarget(), vertex.getId());
                }

            } else {

                vertex.voteToHalt();
            }
        }
    }

    public Map<Integer, List<Integer>> getVerticesGroups(){

        Map<Integer, List<Integer>> result = new HashMap<>();

        Integer[] resultValues = getVertexComputationalValues();


        for (int i = 0; i < resultValues.length; i++) {

            Integer group = resultValues[i];

            if(!result.containsKey(group)){
                result.put(group, new ArrayList<>());
            }

            result.get(group).add(i);
        }

        return result;

    }
}
