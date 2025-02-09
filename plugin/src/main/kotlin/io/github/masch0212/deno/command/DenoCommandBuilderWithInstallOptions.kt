package io.github.masch0212.deno.command

interface DenoCommandBuilderWithInstallOptions<T> {
  /**
   * Allow running npm lifecycle scripts for the given packages.
   *
   * Note: Scripts will only be executed when using a node_modules directory (`--node-modules-dir`).
   *
   * @param packages The packages for which to allow scripts.
   */
  fun allowScripts(vararg packages: String): T

  /**
   * Allow running npm lifecycle scripts for the given packages.
   *
   * Note: Scripts will only be executed when using a node_modules directory (`--node-modules-dir`).
   *
   * @param packages The packages for which to allow scripts.
   */
  fun allowScripts(packages: Iterable<String>): T

  /**
   * Load certificate authority from PEM encoded file.
   *
   * @param cert Path to the certificate authority file.
   */
  fun cert(cert: String): T

  /**
   * Configure different aspects of deno including TypeScript, linting, and code formatting.
   * Typically the configuration file will be called `deno.json` or `deno.jsonc` and automatically
   * detected; in that case this flag is not necessary.
   *
   * [Deno Docs](https://docs.deno.com/go/config)
   *
   * @param config Path to the configuration file.
   */
  fun config(config: String): T

  /**
   * Load environment variables from local file. Only the first environment variable with a given
   * key is used.
   *
   * Existing process environment variables are not overwritten, so if variables with the same names
   * already exist in the environment, their values will be preserved.
   *
   * Where multiple declarations for the same environment variable exist in your .env file, the
   * first one encountered is applied. This is determined by the order of the files you pass as
   * arguments.
   */
  fun envFile(envFile: String): T

  /**
   * Value of `globalThis.location` used by some web APIs.
   *
   * @param location The location.
   */
  fun location(location: String): T

  /** Suppress diagnostic output. */
  fun quiet(): T

  /**
   * Set the random number generator seed.
   *
   * @param seed The seed.
   */
  fun seed(seed: Int): T

  /**
   * Enables the specified unstable features. To view the list of individual unstable feature flags,
   * run `deno run --help=unstable` or `deno eval --help=unstable` respectively.
   *
   * @param flags The unstable feature flags.
   */
  fun unstable(vararg flags: String): T

  /**
   * Enables the specified unstable features. To view the list of individual unstable feature flags,
   * run `deno run --help=unstable` or `deno eval --help=unstable` respectively.
   *
   * @param flags The unstable feature flags.
   */
  fun unstable(flags: Iterable<String>): T

  /**
   * To see a list of all available flags use `deno run --v8-flags=--help`. Flags can also be set
   * via the DENO_V8_FLAGS environment variable. Any flags set with this flag are appended after the
   * DENO_V8_FLAGS environment variable.
   *
   * @param flags The V8 flags.
   */
  fun v8Flags(vararg flags: String): T

  /**
   * To see a list of all available flags use `deno run --v8-flags=--help`. Flags can also be set
   * via the DENO_V8_FLAGS environment variable. Any flags set with this flag are appended after the
   * DENO_V8_FLAGS environment variable.
   *
   * @param flags The V8 flags.
   */
  fun v8Flags(flags: Iterable<String>): T
}

interface DenoCommandBuilderInstallOptionsComposable<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderWithInstallOptions<T>, DenoCommandBuilderComposable<T>

class DenoCommandBuilderInstallOptionsComposableImpl<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderInstallOptionsComposable<T> {
  private lateinit var builder: T

  override fun initialize(builder: T) {
    this.builder = builder
  }

  override fun allowScripts(vararg packages: String) = builder.valueArg("--allow-scripts", packages)

  override fun allowScripts(packages: Iterable<String>) =
      builder.valueArg("--allow-scripts", packages)

  override fun cert(cert: String) = builder.valueArg("--cert", cert)

  override fun config(config: String) = builder.valueArg("--config", config)

  override fun envFile(envFile: String) = builder.valueArg("--env-file", envFile)

  override fun location(location: String) = builder.valueArg("--location", location)

  override fun quiet() = builder.args("--quiet")

  override fun seed(seed: Int) = builder.valueArg("--seed", seed)

  override fun unstable(vararg flags: String) = builder.args(flags.map { "--unstable-$it" })

  override fun unstable(flags: Iterable<String>) = builder.args(flags.map { "--unstable-$it" })

  override fun v8Flags(vararg flags: String) = builder.args(flags.map { "--v8-flags=$it" })

  override fun v8Flags(flags: Iterable<String>) = builder.args(flags.map { "--v8-flags=$it" })
}
