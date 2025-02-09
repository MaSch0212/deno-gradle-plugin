package io.github.masch0212.deno.command

interface DenoCommandBuilderWithRunOptions<T> {
  /**
   * Set content type of the supplied file.
   *
   * Possible values: `ts`, `tsx`, `js`, `jsx`
   *
   * @param ext The file extension.
   */
  fun ext(ext: String): T

  /** Disable V8 code cache feature. */
  fun noCodeCache(): T

  /** Disable automatic loading of the configuration file. */
  fun noConfig(): T
}

interface DenoCommandBuilderRunOptionsComposable<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderWithRunOptions<T>, DenoCommandBuilderComposable<T>

class DenoCommandBuilderRunOptionsComposableImpl<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderRunOptionsComposable<T> {
  private lateinit var builder: T

  override fun initialize(builder: T) {
    this.builder = builder
  }

  override fun ext(ext: String) = builder.valueArg("--ext", ext)

  override fun noCodeCache() = builder.args("--no-code-cache")

  override fun noConfig() = builder.args("--no-config")
}
