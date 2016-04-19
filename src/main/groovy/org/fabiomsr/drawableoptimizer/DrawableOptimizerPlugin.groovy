package org.fabiomsr.drawableoptimizer

import com.android.build.gradle.api.BaseVariant
import org.fabiomsr.drawableoptimizer.extension.DrawableOptimizerExtension

import org.fabiomsr.drawableoptimizer.task.DrawableOptimizerTask
import org.gradle.api.DomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project

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

        variants.all { variant ->

            def variantName = variant.name.capitalize()
            def ext = project.extensions['drawableOptimizer'] as DrawableOptimizerExtension

            def task = project.tasks.create("optimize${variantName}Drawable", DrawableOptimizerTask) {
                it.description = "Drawable optimization"
                it.module = project.name
                it.optimizerType = ext.optimizer
                it.drawableDirs = variant.mergeResources.outputDir
            }

            variant.mergeResources.doLast{ task.execute() }
        }
    }
}
