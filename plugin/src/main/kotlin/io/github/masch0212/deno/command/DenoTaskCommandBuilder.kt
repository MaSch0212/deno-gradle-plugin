@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.github.masch0212.deno.command

import io.github.masch0212.deno.extensions.quoteIfNecessary

class DenoTaskCommandBuilder(val taskName: String, taskArgs: Iterable<String>? = null) :
    DenoCommandBuilderBase<DenoTaskCommandBuilder>() {

  /** The arguments to pass to the task. */
  val taskArgs = taskArgs?.toMutableList() ?: mutableListOf()

  override fun build() =
      DenoCommand(
          sequence {
                yield("task")
                yieldAll(args)
                yield(taskName)
                yieldAll(taskArgs)
              }
              .toList(),
          environment.toMap(),
          workingDir)

  /**
   * Adds a task argument to the command.
   *
   * @param arg The argument to add.
   */
  fun taskArgs(arg: String) = apply { taskArgs.add(arg) }

  /**
   * Adds multiple task arguments to the command.
   *
   * @param args The arguments to add.
   */
  fun taskArgs(vararg args: String) = apply { this.taskArgs.addAll(args) }

  /**
   * Adds multiple task arguments to the command.
   *
   * @param args The arguments to add.
   */
  fun taskArgs(args: Iterable<String>) = apply { this.taskArgs.addAll(args) }

  /**
   * Adds a value argument to the task.
   *
   * @param arg The argument to add.
   */
  fun <V> taskValueArg(arg: String, values: Iterable<V>) = apply {
    if (values.none()) args.add(arg)
    else args.add("$arg=\"${values.joinToString().quoteIfNecessary()}\"")
  }

  /**
   * Adds a value argument to the task.
   *
   * @param arg The argument to add.
   * @param values The values for the argument.
   */
  fun <V> taskValueArg(arg: String, values: Array<V>) = taskValueArg(arg, values.asIterable())

  /**
   * Adds a value argument to the task.
   *
   * @param arg The argument to add.
   * @param value The value for the argument.
   */
  fun taskValueArg(arg: String, value: String?) = apply {
    if (value == null) taskArgs.add(arg) else taskArgs.add("$arg=${value.quoteIfNecessary()}")
  }

  /**
   * Adds a value argument to the task.
   *
   * @param arg The argument to add.
   * @param value The value for the argument.
   */
  fun <T> taskValueArg(arg: String, value: T) = taskValueArg(arg, value?.toString())

  // region Options

  /**
   * Configure different aspects of deno including TypeScript, linting, and code formatting.
   *
   * Typically the configuration file will be called `deno.json` or `deno.jsonc` and automatically
   * detected; in that case this flag is not necessary.
   *
   * [Deno Docs](https://docs.deno.com/go/config)
   */
  fun config(file: String) = valueArg("--config", file)

  /** Specify the directory to run the task in. */
  fun cwd(cwd: String) = valueArg("--cwd", cwd)

  /** Evaluate the passed value as if, it was a task in a configuration file. */
  fun eval(vararg command: String) = args("--eval", *command)

  /** Filter members of the workspace by name - should be used with [recursive]. */
  fun filter(filter: String) = valueArg("--filter", filter)

  /** Suppress diagnostic output */
  fun quiet() = args("--quiet")

  /** Run the task in all projects in the workspace. */
  fun recursive() = args("--recursive")

  /**
   * Enables the specified unstable features. To view the list of individual unstable feature flags,
   * run `deno task --help=unstable`.
   *
   * @param flags The unstable feature flags.
   */
  fun unstable(vararg flags: String) = args(flags.map { "--unstable-$it" })

  /**
   * Enables the specified unstable features. To view the list of individual unstable feature flags,
   * run `deno task --help=unstable`.
   *
   * @param flags The unstable feature flags.
   */
  fun unstable(flags: Iterable<String>) = args(flags.map { "--unstable-$it" })

  // endregion

  // region Dependency management options

  /** Sets the node modules management mode for npm packages. */
  fun nodeModulesDir(nodeModulesDir: String? = null) =
      valueArg("--node-modules-dir", nodeModulesDir)

  // endregion
}
