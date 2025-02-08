package io.github.masch0212.deno.command

interface DenoCommandBuilderWithTypeChecking<T> {
  /**
   * Enable type-checking. The `run` subcommand does not type-check by default.
   *
   * @param all Include remote modules in type-checking. Alternative, the `deno check` subcommand
   *   can be used.
   */
  fun check(all: Boolean = false): T

  /**
   * Skip type-checking.
   *
   * @param remoteOnly Only ignore type-checking for remote modules.
   */
  fun noCheck(remoteOnly: Boolean = false): T
}

interface DenoCommandBuilderTypeCheckingComposable<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderWithTypeChecking<T>, DenoCommandBuilderComposable<T>

class DenoCommandBuilderTypeCheckingComposableImpl<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderTypeCheckingComposable<T> {
  private lateinit var builder: T

  override fun initialize(builder: T) {
    this.builder = builder
  }

  override fun check(all: Boolean) = builder.valueArg("--check", if (all) "all" else null)

  override fun noCheck(remoteOnly: Boolean) =
      builder.valueArg("--no-check", if (remoteOnly) "remote" else null)
}
