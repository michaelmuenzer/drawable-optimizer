package org.fabiomsr.drawableoptimizer.optimizer.impl

import com.googlecode.pngtastic.core.PngImage
import com.googlecode.pngtastic.core.PngOptimizer
import org.fabiomsr.drawableoptimizer.optimizer.Optimizer
import org.fabiomsr.drawableoptimizer.optimizer.OptimizerConstants
import org.fabiomsr.drawableoptimizer.util.ZopfliFileSystemUtils
import org.gradle.api.Project

/**
 * Created by fabiomsr on 19/4/16.
 */
class ZopfliOptimizer implements Optimizer{

    @Override
    void optimize(Project project, int compressionLevel, int iterations, String logLevel, File[] files) {

        ZopfliFileSystemUtils.copyZopfliToBuildFolder(project)

        files.each {
            def originalFileSize = it.length()

            def zopfliPath = ZopfliFileSystemUtils.getZopfliFilePath(project)
            Process process = new ProcessBuilder(zopfliPath, "-y", "--iterations=${iterations}", it.absolutePath, it.absolutePath).start()
            process.waitFor();

            def optimizedFileSize = new File(it.absolutePath).length()

            def fileSizeDifference = (optimizedFileSize <= originalFileSize) ?
                    (originalFileSize - optimizedFileSize) : -(optimizedFileSize - originalFileSize);

            if(logLevel == "debug" || logLevel == "info") {
                printf ("%5.2f%% :%6dB ->%6dB (%5dB saved) - %s\n",
                        fileSizeDifference / Float.valueOf(originalFileSize) * 100,
                        originalFileSize, optimizedFileSize, fileSizeDifference, it.absolutePath);
            }

        }
    }
}