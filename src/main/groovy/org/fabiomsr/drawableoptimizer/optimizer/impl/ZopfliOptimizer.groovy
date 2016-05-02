package org.fabiomsr.drawableoptimizer.optimizer.impl

import com.googlecode.pngtastic.core.PngImage
import com.googlecode.pngtastic.core.PngOptimizer
import org.fabiomsr.drawableoptimizer.optimizer.Optimizer

/**
 * Created by fabiomsr on 19/4/16.
 */
class ZopfliOptimizer implements Optimizer{

    @Override
    void optimize(int compressionLevel, int iterations, String logLevel, String... files) {
        PngOptimizer optimizer = new PngOptimizer(logLevel);
        optimizer.setCompressor('zopfli', iterations);

        files.each {
            PngImage image = new PngImage(it, logLevel)
            optimizer.optimize(image, it, false, null)
        }
    }
}