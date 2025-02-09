package io.github.masch0212.deno.command

import io.github.masch0212.deno.extensions.quoteIfNecessary

class DenoServeCommandBuilder(
    var filePath: String,
    scriptArgs: Iterable<String>? = null,
    runOptions: DenoCommandBuilderRunOptionsComposable<DenoServeCommandBuilder> =
        DenoCommandBuilderRunOptionsComposableImpl(),
    typeChecking: DenoCommandBuilderTypeCheckingComposable<DenoServeCommandBuilder> =
        DenoCommandBuilderTypeCheckingComposableImpl(),
    fileWatching: DenoCommandBuilderFileWatchingComposable<DenoServeCommandBuilder> =
        DenoCommandBuilderFileWatchingComposableImpl(),
    debugging: DenoCommandBuilderDebuggingComposable<DenoServeCommandBuilder> =
        DenoCommandBuilderDebuggingComposableImpl(),
    dependencyManagement:
        DenoCommandBuilderDependencyManagementComposable<DenoServeCommandBuilder> =
        DenoCommandBuilderDependencyManagementComposableImpl(),
    security: DenoCommandBuilderSecurityComposable<DenoServeCommandBuilder> =
        DenoCommandBuilderSecurityComposableImpl()
) :
    DenoCommandBuilderBase<DenoServeCommandBuilder>(),
    DenoCommandBuilderWithRunOptions<DenoServeCommandBuilder> by runOptions,
    DenoCommandBuilderWithTypeChecking<DenoServeCommandBuilder> by typeChecking,
    DenoCommandBuilderWithFileWatching<DenoServeCommandBuilder> by fileWatching,
    DenoCommandBuilderWithDebugging<DenoServeCommandBuilder> by debugging,
    DenoCommandBuilderWithDependencyManagement<DenoServeCommandBuilder> by dependencyManagement,
    DenoCommandBuilderWithSecurity<DenoServeCommandBuilder> by security {

  /** The arguments to pass to the script. */
  val scriptArgs = scriptArgs?.toMutableList() ?: mutableListOf()

  init {
    runOptions.initialize(this)
    typeChecking.initialize(this)
    fileWatching.initialize(this)
    debugging.initialize(this)
    dependencyManagement.initialize(this)
    security.initialize(this)
  }

  override fun build() =
      DenoCommand(
          sequence {
                yield("serve")
                yieldAll(args)
                yield(filePath)
                yieldAll(scriptArgs)
              }
              .toList(),
          environment.toMap(),
          workingDir)

  /**
   * Adds a script argument to the command.
   *
   * @param arg The argument to add.
   */
  fun scriptArgs(arg: String) = apply { scriptArgs.add(arg) }

  /**
   * Adds multiple script arguments to the command.
   *
   * @param args The arguments to add.
   */
  fun scriptArgs(vararg args: String) = apply { this.scriptArgs.addAll(args) }

  /**
   * Adds multiple script arguments to the command.
   *
   * @param args The arguments to add.
   */
  fun scriptArgs(args: Iterable<String>) = apply { this.scriptArgs.addAll(args) }

  /**
   * Adds a value argument to the script.
   *
   * @param arg The argument to add.
   * @param values The values for the argument.
   */
  fun <V> scriptValueArg(arg: String, values: Iterable<V>) = apply {
    if (values.none()) args.add(arg)
    else args.add("$arg=\"${values.joinToString().quoteIfNecessary()}\"")
  }

  /**
   * Adds a value argument to the script.
   *
   * @param arg The argument to add.
   * @param values The values for the argument.
   */
  fun <V> scriptValueArg(arg: String, values: Array<V>) = scriptValueArg(arg, values.asIterable())

  /**
   * Adds a value argument to the script.
   *
   * @param arg The argument to add.
   * @param value The value for the argument.
   */
  fun scriptValueArg(arg: String, value: String?) = apply {
    if (value == null) scriptArgs.add(arg) else scriptArgs.add("$arg=${value.quoteIfNecessary()}")
  }

  /**
   * Adds a value argument to the script.
   *
   * @param arg The argument to add.
   * @param value The value for the argument.
   */
  fun <V> scriptValueArg(arg: String, value: V) = scriptValueArg(arg, value?.toString())

  /**
   * Run multiple server workers in parallel. Parallelism defaults to the number of available CPUs
   * or the value of the DENO_JOBS environment variable.
   */
  fun parallel() = args("--parallel")

  /**
   * The TCP port to serve on. Pass 0 to pick a random free port. (Default: 8080)
   *
   * @param port The port to serve on.
   */
  fun port(port: Int) = valueArg("--port", port)
}
