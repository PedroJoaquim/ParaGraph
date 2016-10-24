package pt.ist.rc.paragraph.computation;

import pt.ist.rc.paragraph.exceptions.InvalidComputationConfigException;

/**
 * Configuration for any vertex centric computation
 */
public final class ComputationConfig {

    /**
     * Number of worker threads
     */
    private int numWorkers;

    /**
     * Empty constructor
     */
    public ComputationConfig() {}

    /**
     *
     * @return Number of worker threads
     */
    public int getNumWorkers() {
        return numWorkers;
    }

    /**
     * @param numWorkers Number of worker threads
     * @return This object for cascading declaration
     */
    public ComputationConfig setNumWorkers(int numWorkers) {

        if(numWorkers <= 0){
            throw new InvalidComputationConfigException("Invalid Number of Workers - Must be a number greater than 0");
        }

        this.numWorkers = numWorkers;
        return this;
    }
}
