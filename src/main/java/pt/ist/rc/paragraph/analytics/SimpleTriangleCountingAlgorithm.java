package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.ComputationalVertex;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.Graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Pedro Joaquim on 27-10-2016
 */
public class SimpleTriangleCountingAlgorithm extends VertexCentricComputation<Object, Object, Integer, String> {


    /**
     * Constructor
     *
     * @param graph  Core graph data
     * @param config Computation configuration
     */
    public SimpleTriangleCountingAlgorithm(Graph<?, ?> graph, ComputationConfig config) {
        super(graph, config);
    }


    @Override
    protected Integer initializeValue(int vertexID) {
        return 0;
    }

    @Override
    protected void compute(ComputationalVertex<?, ?, Integer, String> vertex) {


        if (getSuperStep() == 0) {

            Iterator<? extends Edge<?>> iterator = vertex.getOutEdgesIterator();

            while (iterator.hasNext()) {
                Edge<?> edge = iterator.next();

                if (edge.getTargetIdx() > vertex.getId()) {
                    sendMessageTo(edge.getTargetIdx(), String.valueOf(vertex.getId()));
                }
            }

        } else if (getSuperStep() == 1) {

            Iterator<? extends Edge<?>> iterator = vertex.getOutEdgesIterator();

            List<String> messages = vertex.getMessages();

            while (iterator.hasNext()) {
                Edge<?> edge = iterator.next();

                if (edge.getTargetIdx() > vertex.getId()) {

                    for (String msg : messages) {
                        sendMessageTo(edge.getTargetIdx(), msg + "#" + String.valueOf(vertex.getId()));
                    }
                }
            }


        } else {

            for (String msg : vertex.getMessages()) {

                String[] verticesID = msg.split("#");

                Iterator<? extends Edge<?>> iterator = vertex.getOutEdgesIterator();

                while (iterator.hasNext()) {
                    Edge<?> edge = iterator.next();

                    if (Integer.valueOf(verticesID[0]) == edge.getTargetIdx()) {
                        vertex.setComputationalValue(vertex.getComputationalValue() + 1);
                        break;
                    }
                }
            }
        }

        vertex.voteToHalt();


    }

    @Override
    protected void masterCompute(Iterator<ComputationalVertex<?, ?, Integer, String>> iterator, HashMap<String, Object> globalValues) {
        //do nothing
    }

    @Override
    protected void initializeGlobalObjects(HashMap<String, Object> globalObjects) {
        //do nothing
    }


    public int getTriangleCount(){
        return getVertexComputationalValues().stream().mapToInt(Integer::intValue).sum();
    }
}
