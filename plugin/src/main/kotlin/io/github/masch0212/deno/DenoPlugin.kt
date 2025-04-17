package io.github.masch0212.deno

import io.github.masch0212.deno.services.DenoService
import java.io.File
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project

class DenoPlugin @Inject constructor(private val project: Project) : Plugin<Project> {
  override fun apply(target: Project) {
    // Services
    target.gradle.sharedServices.registerIfAbsent("deno", DenoService::class.java) {
      parameters.cacheRoot = File(project.gradle.gradleUserHomeDir, "caches")
    }

    // Extensions
    target.extensions.create("deno", DenoExtension::class.java)

    // Tasks
    target.tasks.register("installDeno", InstallDenoTask::class.java)
  }
}
