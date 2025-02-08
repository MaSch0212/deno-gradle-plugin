package io.github.masch0212.deno

import io.github.masch0212.deno.extensions.property
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory

abstract class DenoExtension @Inject constructor(objects: ObjectFactory) {
  val version = objects.property<String>("latest")
  val authTokens = objects.property<List<String>?>(null)
  val certificateFile = objects.property<String?>(null)
  val cacheDir = objects.property<String?>(null)
  val installRoot = objects.property<String?>(null)
  val noPackageJson = objects.property<Boolean?>(null)
  val noUpdateCheck = objects.property<Boolean?>(null)
  val tlsCaStore = objects.property<List<String>?>(null)
  val tracePermissions = objects.property<Boolean?>(null)
  val httpProxy = objects.property<String?>(null)
  val httpsProxy = objects.property<String?>(null)
  val noColor = objects.property<Boolean?>(null)
  val noProxy = objects.property<List<String>?>(null)
  val npmConfigRegistry = objects.property<String?>(null)

  fun version(version: String) = apply { this.version.set(version) }

  fun authTokens(tokens: List<String>) = apply { this.authTokens.set(tokens) }

  fun authTokens(vararg tokens: String) = apply { this.authTokens.set(tokens.toList()) }

  fun certificateFile(certificateFile: String) = apply { this.certificateFile.set(certificateFile) }

  fun cacheDir(cacheDir: String) = apply { this.cacheDir.set(cacheDir) }

  fun installRoot(installRoot: String) = apply { this.installRoot.set(installRoot) }

  fun noPackageJson(noPackageJson: Boolean) = apply { this.noPackageJson.set(noPackageJson) }

  fun noUpdateCheck(noUpdateCheck: Boolean) = apply { this.noUpdateCheck.set(noUpdateCheck) }

  fun tlsCaStore(tlsCaStore: List<String>) = apply { this.tlsCaStore.set(tlsCaStore) }

  fun tlsCaStore(vararg tlsCaStore: String) = apply { this.tlsCaStore.set(tlsCaStore.toList()) }

  fun tracePermissions(tracePermissions: Boolean) = apply {
    this.tracePermissions.set(tracePermissions)
  }

  fun httpProxy(httpProxy: String) = apply { this.httpProxy.set(httpProxy) }

  fun httpsProxy(httpsProxy: String) = apply { this.httpsProxy.set(httpsProxy) }

  fun noColor(noColor: Boolean) = apply { this.noColor.set(noColor) }

  fun noProxy(noProxy: List<String>) = apply { this.noProxy.set(noProxy) }

  fun noProxy(vararg noProxy: String) = apply { this.noProxy.set(noProxy.toList()) }

  fun npmConfigRegistry(npmConfigRegistry: String) = apply {
    this.npmConfigRegistry.set(npmConfigRegistry)
  }
}
