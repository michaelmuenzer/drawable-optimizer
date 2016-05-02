package org.fabiomsr.drawableoptimizer.optimizer.impl

import com.googlecode.pngtastic.core.PngImage
import com.googlecode.pngtastic.core.PngOptimizer
import org.fabiomsr.drawableoptimizer.optimizer.Optimizer

/**
 * Created by fabiomsr on 19/4/16.
 */
class DefaultOptimizer implements Optimizer{

    @Override
    void optimize(String folder, String... files) {
        PngOptimizer optimizer = new PngOptimizer("debug");
        optimizer.setCompressor(null, 0);

        files.each {
            println "File to optimize " + it
            PngImage image = new PngImage(it, "debug")
            optimizer.optimize(image, it, false, null)
        }

    }
}

//try {
//    String outputPath = toDir + "/" + file;
//    makeDirs(outputPath.substring(0, outputPath.lastIndexOf('/')));
//
//    PngImage image = new PngImage(file, logLevel);
//    optimizer.optimize(image, outputPath + fileSuffix, removeGamma, compressionLevel);
//
//} catch (PngException | IOException e) {
//    e.printStackTrace();
//}
