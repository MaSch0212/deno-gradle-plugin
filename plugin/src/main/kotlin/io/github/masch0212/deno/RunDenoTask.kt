@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.github.masch0212.deno

import io.github.masch0212.deno.command.*
import io.github.masch0212.deno.extensions.apply
import io.github.masch0212.deno.extensions.denoListProperty
import io.github.masch0212.deno.extensions.denoProperty
import io.github.masch0212.deno.services.DenoService
import io.github.masch0212.deno.utils.Deno
import io.github.masch0212.deno.utils.parseCommandArgs
import java.io.File
import javax.inject.Inject
import kotlin.io.path.Path
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.services.ServiceReference
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.cache.internal.scopes.DefaultGlobalScopedCacheBuilderFactory
import org.gradle.process.ExecOperations

/** Task to run Deno commands. */
abstract class RunDenoTask
@Inject
constructor(
    private val execOperations: ExecOperations,
    private val globalCache: DefaultGlobalScopedCacheBuilderFactory
) : DefaultTask() {
  @get:ServiceReference("deno") protected abstract val denoService: Property<DenoService>

  /** Deno commands to run. */
  @Input val commands = mutableListOf<DenoCommand>()

  /** Environment variables to use for all commands. */
  @Input val environment = mutableMapOf<String, String>()

  /** The working directory for the commands. */
  @Input var workingDir = "."

  /** Deno version to use. */
  @Input var version = denoProperty { version }

  /**
   * A list of bearer tokens and hostnames to use when fetching remote modules from private
   * repositories.
   *
   * @sample ["abcde12345@deno.land", "54321edcba@github.com"]
   */
  @Input @Optional var authTokens = denoListProperty { authTokens }

  /** Load certificate authorities from PEM encoded file. */
  @Input @Optional var certificateFile = denoProperty { certificateFile }

  /**
   * Set the cache directory. `{DENO}` can be used as placeholder for the deno installation folder
   * (in gradle cache). Defaults to `{DENO}/cache`.
   */
  @Input @Optional var cacheDir = denoProperty { cacheDir }

  /** Set deno install's output directory. Defaults to $HOME/.deno/bin. */
  @Input @Optional var installRoot = denoProperty { installRoot }

  /** Disables auto-resolution of package.json. */
  @Input @Optional var noPackageJson = denoProperty { noPackageJson }

  /** Set to disable checking if a newer Deno version is available. */
  @Input @Optional var noUpdateCheck = denoProperty { noUpdateCheck }

  /** A list of order dependent certificate stores. */
  @Input @Optional var tlsCaStore = denoListProperty { tlsCaStore }

  /** Enable stack traces in permission prompts. */
  @Input @Optional var tracePermissions = denoProperty { tracePermissions }

  /** Proxy address for HTTP requests. */
  @Input @Optional var httpProxy = denoProperty { httpProxy }

  /** Proxy address for HTTPS requests. */
  @Input @Optional var httpsProxy = denoProperty { httpsProxy }

  /** Set to disable color in output. */
  @Input @Optional var noColor = denoProperty { noColor }

  /** list of hosts which do not use a proxy (module downloads, fetch). */
  @Input @Optional var noProxy = denoListProperty { noProxy }

  /** URL to use for the npm registry. */
  @Input @Optional var npmConfigRegistry = denoProperty { npmConfigRegistry }

  /** Runs the Deno commands. */
  @TaskAction
  fun runCommands() {
    val deno = denoService.get().get(version)

    logger.debug("Deno: {}", deno)
    logger.debug("Commands: {}", commands)
    logger.debug("Trace permissions: {}", tracePermissions)

    val commandsEnvironment = getCommandsEnvironment(deno)
    commands.forEach { command ->
      if (logger.isInfoEnabled) {
        logger.info(
            "\n--> Deno command: {}",
            command.toString(deno.target, includeEnv = true, additionalEnv = commandsEnvironment))
      } else {
        logger.quiet("\n--> Deno command: {}", command.toString(deno.target, includeEnv = false))
      }

      execOperations.exec {
        workingDir(
            when {
              command.workingDir == null -> File(this@RunDenoTask.workingDir)
              Path(command.workingDir).isAbsolute -> File(command.workingDir)
              else -> File(this@RunDenoTask.workingDir, command.workingDir)
            })
        commandLine(deno.executable)
        args(command.args)
        environment(commandsEnvironment)
        environment(command.env)
      }
    }
  }

  private fun getCommandsEnvironment(deno: Deno) = buildMap {
    authTokens?.also { put("DENO_AUTH_TOKENS", it.joinToString(";")) }
    certificateFile?.also { put("DENO_CERT", it) }
    put(
        "DENO_DIR",
        cacheDir
            .replace("{GRADLE_CACHE}", globalCache.globalCacheRoots.first().absolutePath)
            .replace("{DENO_VERSION}", deno.version)
            .replace("{DENO}", deno.executable.parent))
    installRoot?.also { put("DENO_INSTALL_ROOT", it) }
    noPackageJson?.also { put("DENO_NO_PACKAGE_JSON", if (it) "1" else "0") }
    noUpdateCheck?.also { put("DENO_NO_UPDATE_CHECK", if (it) "1" else "0") }
    tlsCaStore?.also { put("DENO_TLS_CA_STORE", it.joinToString(",")) }
    tracePermissions?.also { put("DENO_TRACE_PERMISSIONS", if (it) "1" else "0") }
    httpProxy?.also { put("HTTP_PROXY", it) }
    httpsProxy?.also { put("HTTPS_PROXY", it) }
    noColor?.also { put("NO_COLOR", if (it) "1" else "0") }
    noProxy?.also { put("NO_PROXY", it.joinToString(",")) }
    npmConfigRegistry?.also { put("NPM_CONFIG_REGISTRY", it) }
    putAll(this@RunDenoTask.environment)
  }

  /**
   * Sets an environment variable for all commands.
   *
   * @param key The environment variable key.
   * @param value The environment variable value.
   */
  fun environment(key: String, value: String?) {
    if (value == null) environment.remove(key) else environment[key] = value
  }

  /**
   * Sets environment variables for all commands.
   *
   * @param env The environment variables.
   */
  fun environment(env: Map<String, String>) {
    environment.putAll(env)
  }

  /**
   * Adds a Deno command to run.
   *
   * @param block The command configuration.
   */
  fun command(block: DenoCommandBuilder.() -> Unit) {
    commands.add(DenoCommandBuilder().apply(block).build())
  }

  /**
   * Adds a Deno command to run.
   *
   * @param commandString The command string.
   * @param env The environment variables.
   */
  fun commandLine(
      commandString: String,
      env: Map<String, String> = emptyMap(),
      workingDir: String? = null
  ) {
    commands.add(
        when {
          commandString.startsWith("deno ") -> commandString.substring(5)
          commandString.startsWith("deno.exe ") -> commandString.substring(9)
          else -> commandString
        }.let { DenoCommand(parseCommandArgs(it), env, workingDir) })
  }

  /**
   * Adds a Deno command to run.
   *
   * @param args The command arguments.
   * @param env The environment variables.
   */
  fun command(
      vararg args: String,
      env: Map<String, String> = emptyMap(),
      workingDir: String? = null
  ) {
    commands.add(DenoCommand(args.toList(), env, workingDir))
  }

  /**
   * Adds a Deno command to run.
   *
   * @param args The command arguments.
   * @param env The environment variables.
   */
  fun command(
      args: Iterable<String>,
      env: Map<String, String> = emptyMap(),
      workingDir: String? = null
  ) {
    commands.add(DenoCommand(args.toList(), env, workingDir))
  }

  /**
   * Adds a Deno `run` command.
   *
   * @param filePath The file path to run.
   * @param args The script arguments.
   * @param configure The command configuration.
   */
  fun run(
      filePath: String,
      vararg args: String,
      configure: (DenoRunCommandBuilder.() -> Unit)? = null
  ) = run(filePath, args.asIterable(), configure)

  /**
   * Adds a Deno `run` command.
   *
   * @param filePath The file path to run.
   * @param args The script arguments.
   * @param configure The command configuration.
   */
  fun run(
      filePath: String,
      args: Iterable<String>? = null,
      configure: (DenoRunCommandBuilder.() -> Unit)? = null
  ) {
    commands.add(DenoRunCommandBuilder(filePath, args).apply(configure).build())
  }

  /**
   * Adds a Deno `serve` command.
   *
   * @param filePath The file path to serve.
   * @param args The script arguments.
   * @param configure The command configuration.
   */
  fun serve(
      filePath: String,
      vararg args: String,
      configure: (DenoServeCommandBuilder.() -> Unit)? = null
  ) = serve(filePath, args.asIterable(), configure)

  /**
   * Adds a Deno `serve` command.
   *
   * @param filePath The file path to serve.
   * @param args The script arguments.
   * @param configure The command configuration.
   */
  fun serve(
      filePath: String,
      args: Iterable<String>? = null,
      configure: (DenoServeCommandBuilder.() -> Unit)? = null
  ) {
    commands.add(DenoServeCommandBuilder(filePath, args).apply(configure).build())
  }

  /**
   * Adds a Deno `task` command.
   *
   * @param taskName The task name.
   * @param args The task arguments.
   * @param configure The command configuration.
   */
  fun task(
      taskName: String,
      vararg args: String,
      configure: (DenoTaskCommandBuilder.() -> Unit)? = null
  ) = task(taskName, args.toList(), configure)

  /**
   * Adds a Deno `task` command.
   *
   * @param taskName The task name.
   * @param args The task arguments.
   * @param configure The command configuration.deno eval -
   */
  fun task(
      taskName: String,
      args: Iterable<String>? = null,
      configure: (DenoTaskCommandBuilder.() -> Unit)? = null
  ) {
    commands.add(DenoTaskCommandBuilder(taskName, args).apply(configure).build())
  }

  /**
   * Adds a Deno `eval` command.
   *
   * @param script The script to evaluate.
   * @param configure The command configuration.
   */
  fun eval(script: String, configure: (DenoEvalCommandBuilder.() -> Unit)? = null) {
    commands.add(DenoEvalCommandBuilder(script).apply(configure).build())
  }

  /**
   * Adds a Deno `install` command.
   *
   * @param packagesToInstall The packages to install.
   * @param configure The command configuration.
   */
  fun install(
      vararg packagesToInstall: String,
      configure: (DenoInstallCommandBuilder.() -> Unit)? = null
  ) = install(packagesToInstall.asIterable(), configure)

  /**
   * Adds a Deno `install` command.
   *
   * @param packagesToInstall The packages to install.
   * @param configure The command configuration.
   */
  fun install(
      packagesToInstall: Iterable<String>,
      configure: (DenoInstallCommandBuilder.() -> Unit)? = null
  ) {
    commands.add(DenoInstallCommandBuilder(packagesToInstall.asIterable()).apply(configure).build())
  }

  /**
   * Adds a Deno `check` command.
   *
   * @param files The files to check.
   * @param configure The command configuration.
   */
  fun check(vararg files: String, configure: (DenoCheckCommandBuilder.() -> Unit)? = null) =
      check(files.asIterable(), configure)

  /**
   * Adds a Deno `check` command.
   *
   * @param files The files to check.
   * @param configure The command configuration.
   */
  fun check(files: Iterable<String>, configure: (DenoCheckCommandBuilder.() -> Unit)? = null) {
    commands.add(DenoCheckCommandBuilder(files).apply(configure).build())
  }
}
