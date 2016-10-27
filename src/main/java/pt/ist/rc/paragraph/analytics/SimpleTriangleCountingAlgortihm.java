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
public class SimpleTriangleCountingAlgortihm extends VertexCentricComputation<Void, Void, Integer, String> {


    /**
     * Constructor
     *
     * @param graphData Core graph data
     * @param config    Computation configuration
     */
    public SimpleTriangleCountingAlgortihm(GraphData<Void, Void> graphData, ComputationConfig config) {
        super(graphData, config);
    }

    @Override
    public Integer initializeValue(int vertexID) {
        return 0;
    }

    @Override
    public void compute(ComputationalVertex<Void, Void, Integer, String> vertex) {


        if (getSuperStep() == 0) {

            Iterator<Edge<Void>> iterator = vertex.getOutEdgesIterator();

            while (iterator.hasNext()) {
                Edge<Void> edge = iterator.next();

                if (edge.getTarget() > vertex.getId()) {
                    sendMessageTo(edge.getTarget(), String.valueOf(vertex.getId()));
                }
            }

        } else if (getSuperStep() == 1) {

            Iterator<Edge<Void>> iterator = vertex.getOutEdgesIterator();

            List<String> messages = vertex.getMessages();

            while (iterator.hasNext()) {
                Edge<Void> edge = iterator.next();

                if (edge.getTarget() > vertex.getId()) {

                    for (String msg : messages) {
                        sendMessageTo(edge.getTarget(), msg + "#" + String.valueOf(vertex.getId()));
                    }
                }
            }


        } else {

            for (String msg : vertex.getMessages()) {

                String[] verticesID = msg.split("#");

                Iterator<Edge<Void>> iterator = vertex.getOutEdgesIterator();

                while (iterator.hasNext()) {
                    Edge<Void> edge = iterator.next();

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
