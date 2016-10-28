package pt.ist.rc.paragraph.analytics;

import pt.ist.rc.paragraph.computation.ComputationConfig;
import pt.ist.rc.paragraph.computation.ComputationalVertex;
import pt.ist.rc.paragraph.computation.VertexCentricComputation;
import pt.ist.rc.paragraph.model.Edge;
import pt.ist.rc.paragraph.model.GraphData;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Pedro Joaquim on 27-10-2016
 */
public class SimpleTriangleCountingAlgortihm extends VertexCentricComputation<Object, Object, Integer, String> {


    /**
     * Constructor
     *
     * @param graphData Core graph data
     * @param config    Computation configuration
     */
    public SimpleTriangleCountingAlgortihm(GraphData<?, ?> graphData, ComputationConfig config) {
        super(graphData, config);
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

                if (edge.getTarget() > vertex.getId()) {
                    sendMessageTo(edge.getTarget(), String.valueOf(vertex.getId()));
                }
            }

        } else if (getSuperStep() == 1) {

            Iterator<? extends Edge<?>> iterator = vertex.getOutEdgesIterator();

            List<String> messages = vertex.getMessages();

            while (iterator.hasNext()) {
                Edge<?> edge = iterator.next();

                if (edge.getTarget() > vertex.getId()) {

                    for (String msg : messages) {
                        sendMessageTo(edge.getTarget(), msg + "#" + String.valueOf(vertex.getId()));
                    }
                }
            }


        } else {

            for (String msg : vertex.getMessages()) {

                String[] verticesID = msg.split("#");

                Iterator<? extends Edge<?>> iterator = vertex.getOutEdgesIterator();

                while (iterator.hasNext()) {
                    Edge<?> edge = iterator.next();

                    if (Integer.valueOf(verticesID[0]) == edge.getTarget()) {
                        vertex.setComputationalValue(vertex.getComputationalValue() + 1);
                        break;
                    }
                }
            }
        }

        vertex.voteToHalt();


    }


    public int getTriangleCount(){
        return getVertexComputationalValues().stream().mapToInt(Integer::intValue).sum();
    }
}
