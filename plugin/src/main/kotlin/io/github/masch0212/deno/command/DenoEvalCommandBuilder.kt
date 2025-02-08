@file:Suppress("MemberVisibilityCanBePrivate")

package io.github.masch0212.deno.command

class DenoEvalCommandBuilder(
    val code: String,
    runOptions: DenoCommandBuilderRunOptionsComposable<DenoEvalCommandBuilder> =
        DenoCommandBuilderRunOptionsComposableImpl(),
    typeChecking: DenoCommandBuilderTypeCheckingComposable<DenoEvalCommandBuilder>
) :
    DenoCommandBuilderBase<DenoEvalCommandBuilder>(),
    DenoCommandBuilderWithRunOptions<DenoEvalCommandBuilder> by runOptions,
    DenoCommandBuilderWithTypeChecking<DenoEvalCommandBuilder> by typeChecking {

  init {
    typeChecking.initialize(this)
  }

  override fun build() =
      DenoCommand(
          sequence {
                yield("eval")
                yieldAll(args)
                yield(code)
              }
              .toList(),
          environment.toMap(),
          workingDir)
}
