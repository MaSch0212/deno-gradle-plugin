package io.github.masch0212.deno.command

interface DenoCommandBuilderWithFileWatching<T> {
  /**
   * Watch for file changes and restart process automatically. Local files from entry point module
   * graph are watched by default. Additional paths might be watched by passing them as arguments to
   * this flag.
   *
   * @param files The files to watch.
   */
  fun watchHmr(vararg files: String): T

  /**
   * Watch for file changes and restart process automatically. Local files from entry point module
   * graph are watched by default. Additional paths might be watched by passing them as arguments to
   * this flag.
   *
   * @param files The files to watch.
   */
  fun watchHmr(files: Iterable<String>): T

  /** Do not clear terminal screen when under watch mode. */
  fun noClearScreen(): T

  /**
   * Watch for file changes and restart process automatically. Local files from entry point module
   * graph are watched by default. Additional paths might be watched by passing them as arguments to
   * this flag.
   *
   * @param files The files to watch.
   */
  fun watch(vararg files: String): T

  /**
   * Watch for file changes and restart process automatically. Local files from entry point module
   * graph are watched by default. Additional paths might be watched by passing them as arguments to
   * this flag.
   *
   * @param files The files to watch.
   */
  fun watch(files: Iterable<String>): T

  /**
   * Exclude provided files/patterns from watch mode.
   *
   * @param files The files to exclude.
   */
  fun watchExclude(vararg files: String): T

  /**
   * Exclude provided files/patterns from watch mode.
   *
   * @param files The files to exclude.
   */
  fun watchExclude(files: Iterable<String>): T
}

interface DenoCommandBuilderFileWatchingComposable<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderWithFileWatching<T>, DenoCommandBuilderComposable<T>

class DenoCommandBuilderFileWatchingComposableImpl<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderFileWatchingComposable<T> {
  private lateinit var builder: T

  override fun initialize(builder: T) {
    this.builder = builder
  }

  override fun watchHmr(vararg files: String) = builder.valueArg("--watch-hmr", files)

  override fun watchHmr(files: Iterable<String>) = builder.valueArg("--watch-hmr", files)

  override fun noClearScreen() = builder.args("--no-clear-screen")

  override fun watch(vararg files: String) = builder.valueArg("--watch", files)

  override fun watch(files: Iterable<String>) = builder.valueArg("--watch", files)

  override fun watchExclude(vararg files: String) = builder.valueArg("--watch-exclude", files)

  override fun watchExclude(files: Iterable<String>) = builder.valueArg("--watch-exclude", files)
}
