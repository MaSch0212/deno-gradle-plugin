@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.github.masch0212.deno.command

class DenoCheckCommandBuilder(
    files: Iterable<String>? = null,
    dependencyManagement:
        DenoCommandBuilderDependencyManagementComposable<DenoCheckCommandBuilder> =
        DenoCommandBuilderDependencyManagementComposableImpl()
) :
    DenoCommandBuilderBase<DenoCheckCommandBuilder>(),
    DenoCommandBuilderWithDependencyManagement<DenoCheckCommandBuilder> by dependencyManagement {

  /** The files to check. */
  val files = files?.toMutableList() ?: mutableListOf()

  init {
    dependencyManagement.initialize(this)
  }

  override fun build() =
      DenoCommand(
          sequence {
                yield("check")
                yieldAll(args)
                yieldAll(files)
              }
              .toList(),
          environment.toMap(),
          workingDir)

  /** Adds files to check. */
  fun files(vararg files: String) = apply { this.files.addAll(files) }

  /** Adds files to check. */
  fun files(files: Iterable<String>) = apply { this.files.addAll(files) }

  /** Type-check all code, including remote modules and npm packages. */
  fun all() = args("--all")

  /**
   * Allow importing from remote hosts. Optionally specify allowed IP addresses and host names, with
   * ports as necessary. Default value:
   * deno.land:443,jsr.io:443,esm.sh:443,cdn.jsdelivr.net:443,raw.githubusercontent.com:443,user.githubusercontent.com:443
   *
   * @param ipsOrHostnames The IP addresses or host names to allow importing from.
   */
  fun allowImport(vararg ipsOrHostnames: String) = valueArg("--allow-import", ipsOrHostnames)

  /**
   * Allow importing from remote hosts. Optionally specify allowed IP addresses and host names, with
   * ports as necessary. Default value:
   * deno.land:443,jsr.io:443,esm.sh:443,cdn.jsdelivr.net:443,raw.githubusercontent.com:443,user.githubusercontent.com:443
   *
   * @param ipsOrHostnames The IP addresses or host names to allow importing from.
   */
  fun allowImport(ipsOrHostnames: Iterable<String>) = valueArg("--allow-import", ipsOrHostnames)

  /**
   * Load certificate authority from PEM encoded file.
   *
   * @param cert The certificate authority file.
   */
  fun cert(cert: String) = valueArg("--cert", cert)

  /**
   * Configure different aspects of deno including TypeScript, linting, and code formatting
   * Typically the configuration file will be called `deno.json` or `deno.jsonc` and automatically
   * detected; in that case this flag is not necessary.
   *
   * [Deno Docs](https://docs.deno.com/go/config)
   *
   * @param config The configuration file.
   */
  fun config(config: String) = valueArg("--config", config)

  /** Type-check code blocks in JSDoc as well as actual code. */
  fun doc() = args("--doc")

  /** Type-check code blocks in JSDoc and Markdown only. */
  fun docOnly() = args("--doc-only")

  /** Disable automatic loading of the configuration file. */
  fun noConfig() = args("--no-config")

  /** Suppress diagnostic output. */
  fun quiet() = args("--quiet")

  /**
   * Enables the specified unstable features. To view the list of individual unstable feature flags,
   * run `deno check --help=unstable`.
   *
   * @param flags The unstable feature flags.
   */
  fun unstable(vararg flags: String) = valueArg("--unstable", flags)

  /**
   * Enables the specified unstable features. To view the list of individual unstable feature flags,
   * run `deno check --help=unstable`.
   *
   * @param flags The unstable feature flags.
   */
  fun unstable(flags: Iterable<String>) = valueArg("--unstable", flags)
}
