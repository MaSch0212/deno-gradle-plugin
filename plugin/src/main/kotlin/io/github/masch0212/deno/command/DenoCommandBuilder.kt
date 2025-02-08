package io.github.masch0212.deno.command

import io.github.masch0212.deno.extensions.quoteIfNecessary

abstract class DenoCommandBuilderBase<T : DenoCommandBuilderBase<T>> {
  val args = mutableListOf<String>()
  val environment = mutableMapOf<String, String>()

  fun args(arg: String) = apply { args.add(arg) }

  fun args(vararg args: String) = apply { this.args.addAll(args) }

  fun args(args: Iterable<String>) = apply { this.args.addAll(args) }

  fun valueArg(arg: String, values: List<String>) = apply {
    if (values.isEmpty()) args.add(arg)
    else args.add("$arg=\"${values.joinToString().quoteIfNecessary()}\"")
  }

  fun valueArg(arg: String, value: String?) = apply {
    if (value == null) args.add(arg) else args.add("$arg=${value.quoteIfNecessary()}")
  }

  fun <T> valueArg(arg: String, value: T) = valueArg(arg, value?.toString())

  fun environment(key: String, value: String?) = apply {
    if (value == null) environment.remove(key) else environment[key] = value
  }

  fun environment(env: Map<String, String>) = apply { this.environment.putAll(env) }

  internal open fun build() = DenoCommand(args.toList(), environment.toMap())

  // region General Options

  // endregion

  protected fun apply(block: T.() -> Unit): T {
    @Suppress("UNCHECKED_CAST") block(this as T)
    return this
  }
}

class DenoCommandBuilder : DenoCommandBuilderBase<DenoCommandBuilder>()
