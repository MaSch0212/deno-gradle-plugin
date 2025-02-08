package io.github.masch0212.deno

import io.github.masch0212.deno.command.DenoCommand
import io.github.masch0212.deno.command.DenoCommandBuilder
import io.github.masch0212.deno.command.DenoRunCommandBuilder
import io.github.masch0212.deno.extensions.apply
import io.github.masch0212.deno.extensions.denoProperty
import io.github.masch0212.deno.services.DenoService
import io.github.masch0212.deno.utils.parseCommandArgs
import java.io.File
import java.util.regex.Pattern
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.services.ServiceReference
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.cache.internal.scopes.DefaultGlobalScopedCacheBuilderFactory
import org.gradle.process.ExecOperations

abstract class RunDenoTask
@Inject
constructor(
    private val execOperations: ExecOperations,
    private val globalCache: DefaultGlobalScopedCacheBuilderFactory
) : DefaultTask() {

  companion object {
    private val COMMAND_PART_REGEX = Pattern.compile("""[^\s"']+|"(.*?)"|'(.*?)'""")
  }

  @get:ServiceReference("deno") abstract val denoService: Property<DenoService>

  @Input val commands = mutableListOf<DenoCommand>()

  @Input var version = denoProperty { version }
  @Input @Optional var authTokens = denoProperty { authTokens }
  @Input @Optional var certificateFile = denoProperty { certificateFile }
  @Input @Optional var cacheDir = denoProperty { cacheDir }
  @Input @Optional var installRoot = denoProperty { installRoot }
  @Input @Optional var noPackageJson = denoProperty { noPackageJson }
  @Input @Optional var noUpdateCheck = denoProperty { noUpdateCheck }
  @Input @Optional var tlsCaStore = denoProperty { tlsCaStore }
  @Input @Optional var tracePermissions = denoProperty { tracePermissions }
  @Input @Optional var httpProxy = denoProperty { httpProxy }
  @Input @Optional var httpsProxy = denoProperty { httpsProxy }
  @Input @Optional var noColor = denoProperty { noColor }
  @Input @Optional var noProxy = denoProperty { noProxy }
  @Input @Optional var npmConfigRegistry = denoProperty { npmConfigRegistry }

  @TaskAction
  fun runCommand() {
    val deno = denoService.get().get(version)

    logger.debug("Deno: {}", deno)
    logger.debug("Commands: {}", commands)
    logger.debug("Trace permissions: {}", tracePermissions)

    commands.forEach { command ->
      logger.quiet("\n> Deno command: {}", command)
      execOperations.exec {
        commandLine(deno.target.executableFileName)
        args(command.args)
        authTokens?.also { environment("DENO_AUTH_TOKENS", it.joinToString(";")) }
        certificateFile?.also { environment("DENO_CERT", it) }
        cacheDir?.also { environment("DENO_DIR", it) }
        installRoot?.also { environment("DENO_INSTALL_ROOT", it) }
        noPackageJson?.also { environment("DENO_NO_PACKAGE_JSON", if (it) "1" else "0") }
        noUpdateCheck?.also { environment("DENO_NO_UPDATE_CHECK", if (it) "1" else "0") }
        tlsCaStore?.also { environment("DENO_TLS_CA_STORE", it.joinToString(",")) }
        tracePermissions?.also { environment("DENO_TRACE_PERMISSIONS", if (it) "1" else "0") }
        httpProxy?.also { environment("HTTP_PROXY", it) }
        httpsProxy?.also { environment("HTTPS_PROXY", it) }
        noColor?.also { environment("NO_COLOR", if (it) "1" else "0") }
        noProxy?.also { environment("NO_PROXY", it.joinToString(",")) }
        npmConfigRegistry?.also { environment("NPM_CONFIG_REGISTRY", it) }
        environment(command.env)
      }
    }
  }

  fun command(block: DenoCommandBuilder.() -> Unit) {
    commands.add(DenoCommandBuilder().apply(block).build())
  }

  fun commandLine(commandString: String, env: Map<String, String> = emptyMap()) {
    commands.add(
        when {
          commandString.startsWith("deno ") -> commandString.substring(5)
          commandString.startsWith("deno.exe ") -> commandString.substring(9)
          else -> commandString
        }.let { DenoCommand(parseCommandArgs(it), env) })
  }

  fun command(vararg args: String, env: Map<String, String> = emptyMap()) {
    commands.add(DenoCommand(args.toList(), env))
  }

  fun command(args: Iterable<String>, env: Map<String, String> = emptyMap()) {
    commands.add(DenoCommand(args.toList(), env))
  }

  fun run(file: File, vararg args: String, configure: (DenoRunCommandBuilder.() -> Unit)? = null) =
      run(file.absolutePath, args.toList(), configure)

  fun run(
      file: File,
      args: Iterable<String>? = null,
      configure: (DenoRunCommandBuilder.() -> Unit)? = null
  ) = run(file.absolutePath, args?.toList(), configure)

  fun run(
      filePath: String,
      vararg args: String,
      configure: (DenoRunCommandBuilder.() -> Unit)? = null
  ) = run(filePath, args.toList(), configure)

  fun run(
      filePath: String,
      args: Iterable<String>? = null,
      configure: (DenoRunCommandBuilder.() -> Unit)? = null
  ) {
    commands.add(DenoRunCommandBuilder(filePath, args).apply(configure).build())
  }
}
