package io.github.masch0212.deno.services

import io.github.masch0212.deno.extensions.extractAll
import io.github.masch0212.deno.utils.Deno
import io.github.masch0212.deno.utils.DenoTarget
import io.github.masch0212.deno.utils.OSInfo
import java.io.File
import java.net.URI
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.zip.ZipFile
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.gradle.internal.cc.base.logger

abstract class DenoService : BuildService<DenoService.Params> {
  interface Params : BuildServiceParameters {
    var cacheRoots: List<File>
  }

  private val id = UUID.randomUUID().toString()

  private val target: DenoTarget by lazy { DenoTarget.fromOsInfo(OSInfo.CURRENT) }

  private val cachedVersions: ConcurrentHashMap<String, Deno> by lazy { findCachedDenoVersions() }

  private val latestVersion: String by lazy { fetchLatestDenoVersion() }

  fun get(version: String) =
      normalizeVersion(version).let { v ->
        cachedVersions[v] ?: throw IllegalArgumentException("Deno $v is not installed.")
      }

  fun getOrDownload(version: String) =
      normalizeVersion(version).let { v ->
        logger.debug("Checking if Deno {} is installed...", v)
        var alreadyInstalled = true
        cachedVersions
            .computeIfAbsent(v) {
              logger.debug("Deno {} is not installed.", v)
              alreadyInstalled = false

              val installDir = File(parameters.cacheRoots.first(), "deno/$v")
              val downloadUrl = "https://dl.deno.land/release/$version/deno-${target.value}.zip"
              val zipFile = File.createTempFile("deno", ".zip").apply { deleteOnExit() }
              val denoExecutable = File(installDir, target.executableFileName)

              installDir.mkdirs()

              logger.quiet("Downloading Deno {}...", version, downloadUrl)
              logger.debug("Downloading Deno {} from {}...", v, downloadUrl)
              URI(downloadUrl).toURL().openStream().use { input ->
                zipFile.outputStream().use { output -> input.copyTo(output) }
              }
              logger.debug("Deno {} downloaded to {}.", v, zipFile)

              logger.quiet("Extracting Deno {}...", v)
              logger.debug("Extracting Deno {} to {}...", v, installDir)
              ZipFile(zipFile).extractAll(installDir)
              logger.debug("Deno {} extracted to {}.", v, installDir)

              require(denoExecutable.exists()) {
                "Deno executable \"${target.executableFileName}\" not found in $installDir"
              }
              Deno(v, denoExecutable, target)
            }
            .also {
              if (alreadyInstalled) {
                logger.quiet("Deno {} is already installed.", v)
              } else {
                logger.quiet("Deno {} installed successfully.", v)
              }
            }
      }

  private fun fetchLatestDenoVersion(): String {
    logger.info("Fetching latest Deno version...")
    return URI("https://dl.deno.land/release-latest.txt").toURL().readText().trim()
  }

  private fun findCachedDenoVersions(): ConcurrentHashMap<String, Deno> {
    logger.info("Checking for cached Deno versions...")

    val result = ConcurrentHashMap<String, Deno>()
    parameters.cacheRoots.forEach { cache ->
      File(cache, "deno").listFiles()?.forEach { versionDir ->
        val denoExecutable = File(versionDir, target.executableFileName)
        if (denoExecutable.exists()) {
          result[versionDir.name] = Deno(versionDir.name, denoExecutable, target)
        }
      }
    }

    logger.debug("Found cached Deno versions: {}", result)
    return result
  }

  private fun normalizeVersion(version: String) =
      when {
        version.startsWith("v") -> version
        version == "latest" -> latestVersion
        else -> "v$version"
      }
}
