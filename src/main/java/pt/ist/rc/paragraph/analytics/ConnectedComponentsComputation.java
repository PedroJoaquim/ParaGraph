package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.GraphData;

import java.util.List;

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
}
