@file:Suppress("MemberVisibilityCanBePrivate")

package io.github.masch0212.deno.command

class DenoEvalCommandBuilder(
    val script: String,
    installOptions: DenoCommandBuilderInstallOptionsComposable<DenoEvalCommandBuilder> =
        DenoCommandBuilderInstallOptionsComposableImpl(),
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
    DenoCommandBuilderWithInstallOptions<DenoEvalCommandBuilder> by installOptions,
    DenoCommandBuilderWithRunOptions<DenoEvalCommandBuilder> by runOptions,
    DenoCommandBuilderWithTypeChecking<DenoEvalCommandBuilder> by typeChecking,
    DenoCommandBuilderWithDebugging<DenoEvalCommandBuilder> by debugging,
    DenoCommandBuilderWithDependencyManagement<DenoEvalCommandBuilder> by dependencyManagement {

  init {
    installOptions.initialize(this)
    runOptions.initialize(this)
    typeChecking.initialize(this)
    debugging.initialize(this)
    dependencyManagement.initialize(this)
  }

  override fun build() =
      DenoCommand(
          sequence {
                yield("eval")
                yieldAll(args)
                yield(script)
              }
              .toList(),
          environment.toMap(),
          workingDir)
}
