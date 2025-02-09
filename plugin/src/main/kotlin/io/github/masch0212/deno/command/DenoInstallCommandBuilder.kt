@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.github.masch0212.deno.command

class DenoInstallCommandBuilder(
    packages: Iterable<String>? = null,
    installOptions: DenoCommandBuilderInstallOptionsComposable<DenoInstallCommandBuilder> =
        DenoCommandBuilderInstallOptionsComposableImpl(),
    typeChecking: DenoCommandBuilderTypeCheckingComposable<DenoInstallCommandBuilder> =
        DenoCommandBuilderTypeCheckingComposableImpl(),
    debugging: DenoCommandBuilderDebuggingComposable<DenoInstallCommandBuilder> =
        DenoCommandBuilderDebuggingComposableImpl(),
    dependencyManagement:
        DenoCommandBuilderDependencyManagementComposable<DenoInstallCommandBuilder> =
        DenoCommandBuilderDependencyManagementComposableImpl(),
    security: DenoCommandBuilderSecurityComposable<DenoInstallCommandBuilder> =
        DenoCommandBuilderSecurityComposableImpl()
) :
    DenoCommandBuilderBase<DenoInstallCommandBuilder>(),
    DenoCommandBuilderWithInstallOptions<DenoInstallCommandBuilder> by installOptions,
    DenoCommandBuilderWithTypeChecking<DenoInstallCommandBuilder> by typeChecking,
    DenoCommandBuilderWithDebugging<DenoInstallCommandBuilder> by debugging,
    DenoCommandBuilderWithDependencyManagement<DenoInstallCommandBuilder> by dependencyManagement,
    DenoCommandBuilderWithSecurity<DenoInstallCommandBuilder> by security {

  /** The packages to install. This is cleared when using [entrypoint]. */
  val packages = packages?.toMutableList() ?: mutableListOf()

  init {
    installOptions.initialize(this)
    typeChecking.initialize(this)
    debugging.initialize(this)
    dependencyManagement.initialize(this)
    security.initialize(this)
  }

  override fun build() =
      DenoCommand(
          sequence {
                yield("install")
                yieldAll(args)
                yieldAll(packages)
              }
              .toList(),
          environment.toMap(),
          workingDir)

  /**
   * Adds a package to install. This clears any instances of [entrypoint].
   *
   * @param package The package to install.
   */
  fun packages(vararg packages: String) =
      apply { this.packages.addAll(packages) }
          .also { args.removeAll { it.startsWith("--entrypoint") } }

  /**
   * Adds a package to install. This clears any instances of [entrypoint].
   *
   * @param package The package to install.
   */
  fun packages(packages: Iterable<String>) =
      apply { this.packages.addAll(packages) }
          .also { args.removeAll { it.startsWith("--entrypoint") } }

  /** Add as a dev dependency. */
  fun dev() = args("--dev")

  /**
   * Install dependents of the specified entrypoint(s). This clears the list of packages to install.
   */
  fun entrypoint(entrypoint: String) =
      valueArg("--entrypoint", entrypoint).also { packages.clear() }

  /** Forcefully overwrite existing installation. */
  fun force() = args("--force")

  /** Install a package or script as a globally available executable. */
  fun global() = args("--global")

  /**
   * Executable file name.
   *
   * @param name The name of the executable.
   */
  fun name(name: String) = valueArg("--name", name)

  /**
   * Installation root.
   *
   * @param root The root directory to install to.
   */
  fun root(root: String) = valueArg("--root", root)
}
