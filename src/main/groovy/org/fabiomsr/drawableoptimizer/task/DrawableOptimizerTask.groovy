package org.fabiomsr.drawableoptimizer.task

import groovy.io.FileType
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

    @InputFiles
    def drawableDirs

    @TaskAction
    void optimize(IncrementalTaskInputs inputs) {
        println "*******:$module:$name"
        println drawableDirs

        println inputs.incremental ? "CHANGED inputs considered out of date"
                : "ALL inputs considered out of date"
    }
}
