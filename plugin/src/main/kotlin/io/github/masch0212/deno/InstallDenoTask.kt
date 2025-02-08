package io.github.masch0212.deno

import io.github.masch0212.deno.services.DenoService
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.services.ServiceReference
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

abstract class InstallDenoTask @Inject constructor(private val execOperations: ExecOperations) :
    DefaultTask() {

  @get:ServiceReference("deno") abstract val denoService: Property<DenoService>

  @Input var version: String = "latest"

  @TaskAction
  fun installDeno() {
    denoService.get().getOrDownload(version)
  }
}
