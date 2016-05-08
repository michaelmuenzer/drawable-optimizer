package org.fabiomsr.drawableoptimizer.optimizer.impl

import com.googlecode.pngtastic.core.PngImage
import com.googlecode.pngtastic.core.PngOptimizer
import org.fabiomsr.drawableoptimizer.optimizer.Optimizer
import org.fabiomsr.drawableoptimizer.util.ZopfliFileSystemUtils
import org.gradle.api.Project

/**
 * Created by fabiomsr on 19/4/16.
 */
class ZopfliOptimizer implements Optimizer{

    @Override
    void optimize(Project project, int compressionLevel, int iterations, String logLevel, File[] files) {
        files.each {
            def zopfliPath = ZopfliFileSystemUtils.getZopfliFilePath(project)
            Process process = new ProcessBuilder(zopfliPath, "-y", it.absolutePath, it.absolutePath).start()
            process.waitFor();
            println "complete $it"
        }
    }
}