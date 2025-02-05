package io.github.masch0212.deno

import org.gradle.api.Plugin
import org.gradle.api.Project

class DenoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("deno", DenoExtension::class.java)

        target.tasks.register("installDeno", InstallDenoTask::class.java) {
            version = extension.version.get()
        }
    }
}