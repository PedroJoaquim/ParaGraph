package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.ComputationalVertex;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.Graph;

import java.util.*;

/**
 * Created by Pedro Joaquim on 17-10-2016
 */
public class ConnectedComponentsComputation extends VertexCentricComputation<Object, Object, Integer, Integer> {

    public ConnectedComponentsComputation(Graph<?, ?> graph, ComputationConfig config) {
        super(graph, config);
    }

    @Override
    protected Integer initializeValue(int vertexID) {
        return vertexID;
    }

    @Override
    protected void compute(ComputationalVertex<?, ?, Integer, Integer> vertex) {

        if(getSuperStep() == 0){

            sendMessageToAllOutNeighbors(vertex, vertex.getId());

        } else {

            int minValue = vertex.getComputationalValue();

            for (Integer msg : vertex.getMessages()) {
                if (msg < minValue){
                    minValue = msg;
                }
            }

            if(minValue < vertex.getComputationalValue()){

                vertex.setComputationalValue(minValue);

                sendMessageToAllOutNeighbors(vertex, minValue);

            } else {

                vertex.voteToHalt();
            }
        }
    }

    @Override
    protected void masterCompute(Iterator<ComputationalVertex<?, ?, Integer, Integer>> iterator, HashMap<String, Object> globalValues) {
        //do nothing
    }

    public Map<Integer, List<Integer>> getVerticesGroups(){

        Map<Integer, List<Integer>> result = new HashMap<>();

        List<Integer> resultValues = getVertexComputationalValues();


        for (int i = 0; i < getNumVertices(); i++) {

            Integer group = resultValues.get(i);

            if(!result.containsKey(group)){
                result.put(group, new ArrayList<>());
            }

            result.get(group).add(i);
        }

        return result;
    }
}
