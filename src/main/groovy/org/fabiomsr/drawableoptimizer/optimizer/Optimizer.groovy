package org.fabiomsr.drawableoptimizer.optimizer

/**
 * Created by fabiomsr on 19/4/16.
 */
interface Optimizer {

    /**
     * Optimize
     * @param compressionLevel The compression level; 0-9 allowed.
     * @param iterations  Number of compression iterations
     * @param logLevel  The level of logging output
     * @param files Images to optimize
     */
    void optimize(int compressionLevel, int iterations, String logLevel, String... files)
}
