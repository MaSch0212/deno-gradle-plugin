package io.github.masch0212.deno

import io.github.masch0212.deno.extensions.denoProperty
import io.github.masch0212.deno.services.DenoService
import io.github.masch0212.deno.utils.DenoTarget
import io.github.masch0212.deno.utils.OSInfo
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.services.ServiceReference
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/** Task to install Deno. */
abstract class InstallDenoTask : DefaultTask() {
  private var _version: String = denoProperty { version }

  @get:ServiceReference("deno") protected abstract val denoService: Property<DenoService>

  /** Deno version to install. */
  @get:Input
  var version: String
    get() = denoService.get().resolveVersion(_version)
    set(version) {
      _version = version
    }

  /** Target platform for Deno. */
  @Input val target: DenoTarget = DenoTarget.fromOsInfo(OSInfo.CURRENT)

  @get:OutputFile
  val executable
    get() = denoService.get().getDenoExecutable(version, target)

  /** Installs Deno. */
  @TaskAction
  fun installDeno() {
    denoService.get().getOrDownload(version)
  }
}
