package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
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
    public void compute(int vertexID, List<Integer> messages) {

        if(getSuperstep() == 0){

            sendMessageToAllOutNeighbors(vertexID, vertexID);
        } else {

            int minValue = getValue(vertexID);

            for (Integer msg : messages) {
                if (msg < minValue){
                    minValue = msg;
                }
            }

            if(minValue < getValue(vertexID)){

                setValue(vertexID, minValue);
                sendMessageToAllOutNeighbors(vertexID, minValue);
            } else {

                voteToHalt(vertexID);
            }
        }
    }

    public Map<Integer, List<Integer>> getVerticesGroups(){

        Map<Integer, List<Integer>> result = new HashMap<>();

        for (int i = 0; i < getNumVertices(); i++) {

            Integer group = getValue(i);

            if(!result.containsKey(group)){
                result.put(group, new ArrayList<>());
            }

            result.get(group).add(i);
        }

        return result;

    }
}
