package org.fabiomsr.drawableoptimizer.task

import groovy.io.FileType
import org.fabiomsr.drawableoptimizer.optimizer.Optimizer
import org.fabiomsr.drawableoptimizer.optimizer.OptimizerFactory
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

/**
 *  Created by fabiomsr on 12/3/16.
 */
class DrawableOptimizerTask extends DefaultTask {

    @Input
    def module

    @Input
    def optimizerType

    @InputFiles
    def drawableDirs

    @TaskAction
    void optimize(IncrementalTaskInputs inputs) {
        println "$module:$name"
        println drawableDirs

        def optimizer = OptimizerFactory.INSTANCE.getOptimizer(optimizerType)

        inputs.outOfDate {
            def changedFile = it.file
            if (changedFile.isDirectory()) {
                optimizeDirectory(changedFile, optimizer)
            }else {
                def filePath = changedFile.absolutePath

                if(filePath =~ ~/.*\.png/ &&
                    !filePath.contains(".9.png")) {
                    optimizer.optimize(filePath, filePath)
                }
            }
        }
    }

    def optimizeDirectory(File directory, Optimizer optimizer) {
        directory.eachFileMatch(~/drawable(.)*/) {
            println "Dir out of date: ${it.name}"

            def imageFiles = []
            it.eachFileMatch(FileType.FILES, ~/.*\.png/) { drawable ->
                if (!drawable.name.contains(".9.png")) {
                    imageFiles << drawable.absolutePath
                }
            }

            if (imageFiles) {
                optimizer.optimize(it.name, *imageFiles)
            }
        }
    }

}
