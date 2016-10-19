package pt.ist.rc.paragraph.computation;

import pt.ist.rc.paragraph.exceptions.InvalidComputationConfigException;

/**
 * Created by Pedro Joaquim.
 */
public final class ComputationConfig {

    private int numWorkers;

    public ComputationConfig() {}

    public int getNumWorkers() {
        return numWorkers;
    }

    public ComputationConfig setNumWorkers(int numWorkers) {

        if(numWorkers <= 0){
            throw new InvalidComputationConfigException("Invalid Number of Workers - Must be a number greater than 0");
        }

        this.numWorkers = numWorkers;
        return this;
    }
}
