package pt.ist.rc.paragraph.computation;

/**
 * Created by Pedro Joaquim.
 */
public class ComputationConfig {

    private int numWorkers;

    public ComputationConfig() {}

    public int getNumWorkers() {
        return numWorkers;
    }

    public ComputationConfig setNumWorkers(int numWorkers) {
        this.numWorkers = numWorkers;
        return this;
    }
}
