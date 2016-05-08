package org.fabiomsr.drawableoptimizer.optimizer.impl

import com.googlecode.pngtastic.core.PngImage
import com.googlecode.pngtastic.core.PngOptimizer
import org.fabiomsr.drawableoptimizer.optimizer.Optimizer
import org.gradle.api.Project

/**
 * Created by fabiomsr on 19/4/16.
 */
class PngtasticOptimizer implements Optimizer{
    
    @Override
    void optimize(Project project, int compressionLevel, int iterations, String logLevel, File[] files) {
        PngOptimizer optimizer = new PngOptimizer(logLevel);
        optimizer.setCompressor(null, 0);

        files.each {
            PngImage image = new PngImage(it.absolutePath, logLevel)
            optimizer.optimize(image, it.absolutePath, false, null)
        }
    }
}