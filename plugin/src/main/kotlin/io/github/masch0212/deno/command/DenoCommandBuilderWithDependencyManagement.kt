package io.github.masch0212.deno.command

/** A command builder that supports dependency management options. */
interface DenoCommandBuilderWithDependencyManagement<T> {
  /** Require that remote dependencies are already cached. */
  fun cachedOnly(): T

  /**
   * Error out if lockfile is out of date.
   *
   * @param frozen If true, lockfile is required and must be up-to-date. If false, lockfile is
   *   optional.
   */
  fun frozen(frozen: Boolean = true): T

  /**
   * Load import map file from local file or remote URL
   *
   * [Deno Docs](https://docs.deno.com/runtime/manual/basics/import_maps)
   *
   * @param importMapFile Path to the import map file.
   */
  fun importMap(importMapFile: String): T

  /**
   * Check the specified lock file. (If value is not provided, defaults to "./deno.lock")
   *
   * @param lockFile The lock file to check.
   */
  fun lock(lockFile: String): T

  /** Disable auto discovery of the lock file. */
  fun noLock(): T

  /** Do not resolve npm modules. */
  fun noNpm(): T

  /** Do not resolve remote modules. */
  fun noRemote(): T

  /** Sets the node modules management mode for npm packages. */
  fun nodeModulesDir(mode: String?): T

  /**
   * Reload source code cache (recompile TypeScript)
   * - []: Reload everything
   * - [[<b>jsr:@std/http/file-server</b>, <b>jsr:@std/assert/assert-equals</b>]]: Reloads specific
   *   modules
   * - [[<b>npm</b>]]: Reload all npm modules
   * - [[<b>npm:chalk</b>]]: Reload specific npm module
   *
   * @param cacheBlocklist The cache blocklist.
   */
  fun reload(vararg cacheBlocklist: String): T

  /**
   * Reload source code cache (recompile TypeScript)
   * - []: Reload everything
   * - [[<b>jsr:@std/http/file-server</b>, <b>jsr:@std/assert/assert-equals</b>]]: Reloads specific
   *   modules
   * - [[<b>npm</b>]]: Reload all npm modules
   * - [[<b>npm:chalk</b>]]: Reload specific npm module
   *
   * @param cacheBlocklist The cache blocklist.
   */
  fun reload(cacheBlocklist: Iterable<String>): T

  /**
   * Toggles local vendor folder usage for remote modules and a node_modules folder for npm
   * packages.
   *
   * @param vendor If true, use a local vendor folder for remote modules and a node_modules folder
   *   for npm packages.
   */
  fun vendor(vendor: Boolean = true): T
}

interface DenoCommandBuilderDependencyManagementComposable<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderWithDependencyManagement<T>, DenoCommandBuilderComposable<T>

class DenoCommandBuilderDependencyManagementComposableImpl<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderDependencyManagementComposable<T> {
  private lateinit var builder: T

  override fun initialize(builder: T) {
    this.builder = builder
  }

  override fun cachedOnly() = builder.args("--cached-only")

  override fun frozen(frozen: Boolean) = builder.valueArg("--frozen", frozen)

  override fun importMap(importMapFile: String) = builder.valueArg("--import-map", importMapFile)

  override fun lock(lockFile: String) = builder.valueArg("--lock", lockFile)

  override fun noLock() = builder.args("--no-lock")

  override fun noNpm() = builder.args("--no-npm")

  override fun noRemote() = builder.args("--no-remote")

  override fun nodeModulesDir(mode: String?) = builder.valueArg("--node-modules-dir", mode)

  override fun reload(vararg cacheBlocklist: String) = builder.valueArg("--reload", cacheBlocklist)

  override fun reload(cacheBlocklist: Iterable<String>) =
      builder.valueArg("--reload", cacheBlocklist)

  override fun vendor(vendor: Boolean) = builder.valueArg("--vendor", vendor)
}
