@file:Suppress("MemberVisibilityCanBePrivate")

package io.github.masch0212.deno.command

class DenoEvalCommandBuilder(
    val code: String,
    runOptions: DenoCommandBuilderRunOptionsComposable<DenoEvalCommandBuilder> =
        DenoCommandBuilderRunOptionsComposableImpl(),
    typeChecking: DenoCommandBuilderTypeCheckingComposable<DenoEvalCommandBuilder> =
        DenoCommandBuilderTypeCheckingComposableImpl(),
    debugging: DenoCommandBuilderDebuggingComposable<DenoEvalCommandBuilder> =
        DenoCommandBuilderDebuggingComposableImpl(),
    dependencyManagement: DenoCommandBuilderDependencyManagementComposable<DenoEvalCommandBuilder> =
        DenoCommandBuilderDependencyManagementComposableImpl()
) :
    DenoCommandBuilderBase<DenoEvalCommandBuilder>(),
    DenoCommandBuilderWithRunOptions<DenoEvalCommandBuilder> by runOptions,
    DenoCommandBuilderWithTypeChecking<DenoEvalCommandBuilder> by typeChecking,
    DenoCommandBuilderWithDebugging<DenoEvalCommandBuilder> by debugging,
    DenoCommandBuilderWithDependencyManagement<DenoEvalCommandBuilder> by dependencyManagement {

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
