package org.fabiomsr.drawableoptimizer.task

import com.googlecode.pngtastic.PngtasticOptimizer
import com.googlecode.pngtastic.core.PngOptimizer
import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

/**
 * Created by fabiomsr on 13/3/16.
 */
class OptimizeMainDrawablesTask extends DefaultTask {

    @Input
    def module

    @InputFiles
    def drawableDirs

    @OutputDirectory
    def File outputDir

    @TaskAction
    void optimize(IncrementalTaskInputs inputs) {
        println "*******:$module:$name"
        println drawableDirs

        inputs.outOfDate { change ->

            def changedFile = change.file

            if (changedFile.isDirectory()) {
                changedFile.eachFileMatch(~/drawable(.)*/) {
                    println "Dir out of date: ${it.name}"

                    def filesNames = []
                    it.eachFileMatch(FileType.FILES, ~/.*\.png/) { drawable ->
                        if (!drawable.name.contains(".9.png")) {
                            filesNames << drawable.absolutePath
                        }
                    }

                    optimizeFiles(it.name, *filesNames)
                }
            } else {
                println "out of date: ${change.file.name}"
            }
        }
    }

    def optimizeFiles(String folderName, String... filesNames) {
        String to = outputDir.getAbsolutePath() + File.separator + folderName
        new PngtasticOptimizer(to, filesNames, "", false,9, null, "debug")
    }
}
