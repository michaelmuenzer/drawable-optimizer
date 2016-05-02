package org.fabiomsr.drawableoptimizer.optimizer

import org.fabiomsr.drawableoptimizer.optimizer.impl.DefaultOptimizer
import org.fabiomsr.drawableoptimizer.optimizer.impl.ZopfliOptimizer

/**
 * Created by fabiomsr on 19/4/16.
 */
enum OptimizerFactory {
    INSTANCE;

    def optimizers

    private OptimizerFactory() {
        optimizers = [(OptimizerConstants.DEFAULT): new DefaultOptimizer(),
                      (OptimizerConstants.ZOPFLI) : new ZopfliOptimizer()]
    }

    def Optimizer getOptimizer(def optimizerType){
        return optimizers[optimizerType] as Optimizer
    }

}
