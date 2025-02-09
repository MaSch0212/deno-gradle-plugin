@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.github.masch0212.deno.command

import io.github.masch0212.deno.extensions.quoteIfNecessary

class DenoRunCommandBuilder(
    val filePath: String,
    scriptArgs: Iterable<String>? = null,
    runOptions: DenoCommandBuilderRunOptionsComposable<DenoRunCommandBuilder> =
        DenoCommandBuilderRunOptionsComposableImpl(),
    typeChecking: DenoCommandBuilderTypeCheckingComposable<DenoRunCommandBuilder> =
        DenoCommandBuilderTypeCheckingComposableImpl(),
    fileWatching: DenoCommandBuilderFileWatchingComposable<DenoRunCommandBuilder> =
        DenoCommandBuilderFileWatchingComposableImpl(),
    debugging: DenoCommandBuilderDebuggingComposable<DenoRunCommandBuilder> =
        DenoCommandBuilderDebuggingComposableImpl(),
    dependencyManagement: DenoCommandBuilderDependencyManagementComposable<DenoRunCommandBuilder> =
        DenoCommandBuilderDependencyManagementComposableImpl(),
    security: DenoCommandBuilderSecurityComposable<DenoRunCommandBuilder> =
        DenoCommandBuilderSecurityComposableImpl()
) :
    DenoCommandBuilderBase<DenoRunCommandBuilder>(),
    DenoCommandBuilderWithRunOptions<DenoRunCommandBuilder> by runOptions,
    DenoCommandBuilderWithTypeChecking<DenoRunCommandBuilder> by typeChecking,
    DenoCommandBuilderWithFileWatching<DenoRunCommandBuilder> by fileWatching,
    DenoCommandBuilderWithDebugging<DenoRunCommandBuilder> by debugging,
    DenoCommandBuilderWithDependencyManagement<DenoRunCommandBuilder> by dependencyManagement,
    DenoCommandBuilderWithSecurity<DenoRunCommandBuilder> by security {

  /** The arguments to pass to the script. */
  val scriptArgs = scriptArgs?.toMutableList() ?: mutableListOf()

  init {
    security.initialize(this)
    typeChecking.initialize(this)
  }

  override fun build() =
      DenoCommand(
          sequence {
                yield("run")
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
}
