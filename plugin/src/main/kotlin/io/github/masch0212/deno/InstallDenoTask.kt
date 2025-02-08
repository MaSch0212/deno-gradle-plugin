package io.github.masch0212.deno

import io.github.masch0212.deno.extensions.denoProperty
import io.github.masch0212.deno.services.DenoService
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.services.ServiceReference
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/** Task to install Deno. */
abstract class InstallDenoTask : DefaultTask() {
  @get:ServiceReference("deno") protected abstract val denoService: Property<DenoService>

  /** Deno version to install. */
  @Input var version: String = denoProperty { version }

  /** Installs Deno. */
  @TaskAction
  fun installDeno() {
    denoService.get().getOrDownload(version)
  }
}
