package io.github.masch0212.deno.command

import io.github.masch0212.deno.extensions.quoteIfNecessary

class DenoRunCommandBuilder(
    val filePath: String,
    args: Iterable<String>? = null,
    security: DenoCommandBuilderSecurityComposable<DenoRunCommandBuilder> =
        DenoCommandBuilderSecurityComposableImpl()
) :
    DenoCommandBuilderBase<DenoRunCommandBuilder>(),
    DenoCommandBuilderWithSecurity<DenoRunCommandBuilder> by security {

  val scriptArgs = args?.toMutableList() ?: mutableListOf()

  init {
    security.initialize(this)
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
          environment.toMap())

  fun scriptArgs(arg: String) = apply { scriptArgs.add(arg) }

  fun scriptArgs(vararg args: String) = apply { this.scriptArgs.addAll(args) }

  fun scriptArgs(args: Iterable<String>) = apply { this.scriptArgs.addAll(args) }

  fun scriptValueArg(arg: String, values: List<String>) = apply {
    if (values.isEmpty()) scriptArgs.add(arg)
    else scriptArgs.add("$arg=\"${values.joinToString().quoteIfNecessary()}\"")
  }

  fun scriptValueArg(arg: String, value: String?) = apply {
    if (value == null) scriptArgs.add(arg) else scriptArgs.add("$arg=${value.quoteIfNecessary()}")
  }

  fun <T> scriptValueArg(arg: String, value: T) = valueArg(arg, value?.toString())
}
