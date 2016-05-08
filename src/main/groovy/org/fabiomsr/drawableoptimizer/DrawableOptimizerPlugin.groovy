package org.fabiomsr.drawableoptimizer

import com.android.build.gradle.api.BaseVariant
import org.apache.tools.ant.taskdefs.condition.Os
import org.fabiomsr.drawableoptimizer.extension.DrawableOptimizerExtension
import org.fabiomsr.drawableoptimizer.optimizer.OptimizerConstants
import org.fabiomsr.drawableoptimizer.task.DrawableOptimizerTask
import org.fabiomsr.drawableoptimizer.util.ZopfliFileSystemUtils
import org.gradle.api.DomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.nio.file.Files

/**
 *  Created by fabio on 12/3/16.
 */
class DrawableOptimizerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (project.plugins.hasPlugin('com.android.application')) {
            applyAndroid(project, (DomainObjectCollection<BaseVariant>) project.android.applicationVariants);
        } else if (project.plugins.hasPlugin('com.android.library')) {
            applyAndroid(project, (DomainObjectCollection<BaseVariant>) project.android.libraryVariants);
        } else {
            throw new IllegalArgumentException('DrawableOptimizer plugin requires the Android plugin to be configured');
        }
    }

    private static void applyAndroid(Project project, DomainObjectCollection<BaseVariant> variants) {
        project.extensions.create('drawableOptimizer', DrawableOptimizerExtension)

        def ext = project.extensions['drawableOptimizer'] as DrawableOptimizerExtension

        if(ext.optimizer == OptimizerConstants.ZOPFLI){
            ZopfliFileSystemUtils.copyZopfliToBuildFolder(project)
        }

        variants.all { variant ->
            def variantName = variant.name.capitalize()

            if(ext.onlyOnRelease && !'release'.equalsIgnoreCase(variant.buildType.name) ){
                return
            }

            def task = project.tasks.create("optimize${variantName}Drawable", DrawableOptimizerTask) {
                it.description = "Drawable optimization"
                it.module = project.name
                it.optimizerType    = ext.optimizer
                it.compressionLevel = ext.compressionLevel
                it.iterations   = ext.iterations
                it.logLevel     = ext.logLevel
                it.drawableDirs = variant.mergeResources.outputDir
            }

            variant.mergeResources.doLast { task.execute() }
        }
    }


}
