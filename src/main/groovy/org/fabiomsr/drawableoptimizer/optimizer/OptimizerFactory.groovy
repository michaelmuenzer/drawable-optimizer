package org.fabiomsr.drawableoptimizer.optimizer

import org.fabiomsr.drawableoptimizer.optimizer.impl.DefaultOptimizer
import org.fabiomsr.drawableoptimizer.optimizer.impl.ZopfliOptimizer

/**
 * Created by fabiomsr on 19/4/16.
 */
enum OptimizerFactory {
    INSTANCE;

    public static DEFAULT = 'default'
    public static ZOPFLI = 'zopfli'

    def optimizers

    private OptimizerFactory() {
        optimizers = [(DEFAULT): new DefaultOptimizer(),
                      (ZOPFLI) : new ZopfliOptimizer()]
    }

    def Optimizer getOptimizer(String optimizerType){
        return optimizers[optimizerType] as Optimizer
    }

}
