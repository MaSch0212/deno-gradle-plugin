@file:Suppress("unused")

package io.github.masch0212.deno

import io.github.masch0212.deno.extensions.listProperty
import io.github.masch0212.deno.extensions.property
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory

/**
 * The Deno extension allows you to configure the Deno tasks in your build script.
 *
 * @see DenoPlugin
 */
abstract class DenoExtension @Inject constructor(objects: ObjectFactory) {
  /** The version tasks should use by default. */
  val version = objects.property<String>("latest")

  /**
   * A list of bearer tokens and hostnames to use when fetching remote modules from private
   * repositories.
   *
   * @sample ["abcde12345@deno.land", "54321edcba@github.com"]
   */
  val authTokens = objects.listProperty<String>(null)

  /** Load certificate authorities from PEM encoded file. */
  val certificateFile = objects.property<String?>(null)

  /**
   * Set the cache directory. The following placeholders can be used:
   * - `{GRADLE_CACHE}`: Gradle cache directory.
   * - `{DENO_VERSION}`: Deno version.
   * - `{DENO}`: Deno installation folder (basically `{GRADLE_CACHE}/deno/{DENO_VERSION}`).
   *
   * Defaults to `{DENO}/cache`.
   */
  val cacheDir = objects.property<String>("{DENO}/cache")

  /** Set deno install's output directory. Defaults to $HOME/.deno/bin. */
  val installRoot = objects.property<String?>(null)

  /** Disables auto-resolution of package.json. */
  val noPackageJson = objects.property<Boolean?>(null)

  /** Set to disable checking if a newer Deno version is available. */
  val noUpdateCheck = objects.property<Boolean?>(null)

  /** A list of order dependent certificate stores. */
  val tlsCaStore = objects.listProperty<String>(null)

  /** Enable stack traces in permission prompts. */
  val tracePermissions = objects.property<Boolean?>(null)

  /** Proxy address for HTTP requests. */
  val httpProxy = objects.property<String?>(null)

  /** Proxy address for HTTPS requests. */
  val httpsProxy = objects.property<String?>(null)

  /** Set to disable color in output. */
  val noColor = objects.property<Boolean?>(null)

  /** list of hosts which do not use a proxy (module downloads, fetch). */
  val noProxy = objects.listProperty<String>(null)

  /** URL to use for the npm registry. */
  val npmConfigRegistry = objects.property<String?>(null)

  /** Set the version tasks should use by default. */
  fun version(version: String) = apply { this.version.set(version) }

  /**
   * Sets the list of bearer tokens and hostnames to use when fetching remote modules from private
   * repositories.
   */
  fun authTokens(tokens: List<String>) = apply { this.authTokens.set(tokens) }

  /**
   * Sets the list of bearer tokens and hostnames to use when fetching remote modules from private
   * repositories.
   */
  fun authTokens(vararg tokens: String) = apply { this.authTokens.set(tokens.toList()) }

  /** Sets the file path to load certificate authorities from. */
  fun certificateFile(certificateFile: String) = apply { this.certificateFile.set(certificateFile) }

  /** Sets the cache directory. */
  fun cacheDir(cacheDir: String) = apply { this.cacheDir.set(cacheDir) }

  /** Sets deno install's output directory. Defaults to $HOME/.deno/bin. */
  fun installRoot(installRoot: String) = apply { this.installRoot.set(installRoot) }

  /** Disables/Enables auto-resolution of package.json. */
  fun noPackageJson(noPackageJson: Boolean = true) = apply { this.noPackageJson.set(noPackageJson) }

  /** Disables/enables checking if a newer Deno version is available. */
  fun noUpdateCheck(noUpdateCheck: Boolean = true) = apply { this.noUpdateCheck.set(noUpdateCheck) }

  /** Sets the list of order dependent certificate stores. */
  fun tlsCaStore(tlsCaStore: List<String>) = apply { this.tlsCaStore.set(tlsCaStore) }

  /** Sets the list of order dependent certificate stores. */
  fun tlsCaStore(vararg tlsCaStore: String) = apply { this.tlsCaStore.set(tlsCaStore.toList()) }

  /** Enables/disables stack traces in permission prompts. */
  fun tracePermissions(tracePermissions: Boolean = true) = apply {
    this.tracePermissions.set(tracePermissions)
  }

  /** Sets the proxy address for HTTP requests. */
  fun httpProxy(httpProxy: String) = apply { this.httpProxy.set(httpProxy) }

  /** Sets the proxy address for HTTPS requests. */
  fun httpsProxy(httpsProxy: String) = apply { this.httpsProxy.set(httpsProxy) }

  /** Disables/enables color in output. */
  fun noColor(noColor: Boolean = true) = apply { this.noColor.set(noColor) }

  /** Sets the list of hosts which do not use a proxy. */
  fun noProxy(noProxy: List<String>) = apply { this.noProxy.set(noProxy) }

  /** Sets the list of hosts which do not use a proxy. */
  fun noProxy(vararg noProxy: String) = apply { this.noProxy.set(noProxy.toList()) }

  /** Sets the URL to use for the npm registry. */
  fun npmConfigRegistry(npmConfigRegistry: String) = apply {
    this.npmConfigRegistry.set(npmConfigRegistry)
  }
}
