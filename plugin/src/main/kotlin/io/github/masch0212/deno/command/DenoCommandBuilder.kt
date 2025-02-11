package io.github.masch0212.deno.command

import io.github.masch0212.deno.extensions.quoteIfNecessary

abstract class DenoCommandBuilderBase<T : DenoCommandBuilderBase<T>> {
  /** The arguments to pass to the command. */
  val args = mutableListOf<String>()

  /** The environment variables to pass to the command. */
  val environment = mutableMapOf<String, String>()

  /** The working directory for the command. */
  var workingDir: String? = null

  /**
   * Adds an argument to the command.
   *
   * @param arg The argument to add.
   */
  fun args(arg: String) = apply { args.add(arg) }

  /**
   * Adds multiple arguments to the command.
   *
   * @param args The arguments to add.
   */
  fun args(vararg args: String) = apply { this.args.addAll(args) }

  /**
   * Adds multiple arguments to the command.
   *
   * @param args The arguments to add.
   */
  fun args(args: Iterable<String>) = apply { this.args.addAll(args) }

  /**
   * Adds a value argument to the command.
   *
   * @param arg The argument to add.
   * @param values The values for the argument.
   */
  fun <V> valueArg(arg: String, values: Iterable<V>) = apply {
    if (values.none()) args.add(arg)
    else args.add("$arg=\"${values.joinToString().quoteIfNecessary()}\"")
  }

  /**
   * Adds a value argument to the command.
   *
   * @param arg The argument to add.
   * @param values The values for the argument.
   */
  fun <V> valueArg(arg: String, values: Array<V>) = valueArg(arg, values.asIterable())

  /**
   * Adds a value argument to the command.
   *
   * @param arg The argument to add.
   * @param value The value for the argument.
   */
  fun valueArg(arg: String, value: String?) = apply {
    if (value == null) args.add(arg) else args.add("$arg=${value.quoteIfNecessary()}")
  }

  /**
   * Adds a value argument to the command.
   *
   * @param arg The argument to add.
   * @param value The value for the argument.
   */
  fun <V> valueArg(arg: String, value: V): T = valueArg(arg, value?.toString())

  /**
   * Adds an environment variable to the command.
   *
   * @param key The environment variable key.
   * @param value The environment variable value.
   */
  fun environment(key: String, value: String?) = apply {
    if (value == null) environment.remove(key) else environment[key] = value
  }

  /**
   * Adds environment variables to the command.
   *
   * @param env The environment variables.
   */
  fun environment(env: Map<String, String>) = apply { this.environment.putAll(env) }

  /**
   * Sets the working directory for the command.
   *
   * @param workingDir The working directory.
   */
  fun workingDir(workingDir: String) = apply { this.workingDir = workingDir }

  /** Builds the command. */
  internal open fun build() = DenoCommand(args.toList(), environment.toMap(), workingDir)

  protected fun apply(block: T.() -> Unit): T {
    @Suppress("UNCHECKED_CAST") block(this as T)
    return this
  }
}

/** A builder for creating any Deno command. */
class DenoCommandBuilder : DenoCommandBuilderBase<DenoCommandBuilder>()
