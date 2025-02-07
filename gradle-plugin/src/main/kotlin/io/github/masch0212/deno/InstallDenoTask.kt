package io.github.masch0212.deno

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import java.io.File
import java.net.URI
import javax.inject.Inject

open class InstallDenoTask
    @Inject
    constructor(
        private val execOperations: ExecOperations,
    ) : DefaultTask() {
        @Input
        @Optional
        var version: String = "latest"

        @TaskAction
        fun installDeno() {
            val osTarget = detectOsTarget()
            val cacheDir = File(project.gradle.gradleUserHomeDir, "caches/deno")
            val denoVersion =
                when {
                    version == "latest" -> fetchLatestDenoVersion()
                    !version.startsWith("v") -> "v$version"
                    else -> version
                }
            val installDir = File(cacheDir, denoVersion)
            val denoExecutable = File(installDir, "deno${if (osTarget.contains("windows")) ".exe" else ""}")

            if (denoExecutable.exists()) {
                println("✅ Deno $denoVersion is already installed at $installDir")
                return
            }

            println("🔽 Installing Deno $denoVersion...")

            // Ensure install directory exists
            installDir.mkdirs()

            // Download and extract Deno
            val downloadUrl = "https://dl.deno.land/release/$denoVersion/deno-$osTarget.zip"
            val zipFile = File(installDir, "deno.zip")

            val unzipTool = determineUnzipTool()
            downloadFile(downloadUrl, zipFile)
            extractZip(zipFile, installDir, unzipTool)

            // Make executable
            denoExecutable.setExecutable(true)

            println("🎉 Deno $denoVersion installed successfully at $installDir")
        }

        private fun fetchLatestDenoVersion(): String {
            println("🔍 Fetching latest Deno version...")
            return URI("https://dl.deno.land/release-latest.txt").toURL().readText().trim()
        }

        private fun detectOsTarget(): String {
            val osName = System.getProperty("os.name").lowercase()
            val arch = System.getProperty("os.arch").lowercase()

            return when {
                osName.contains("win") -> "x86_64-pc-windows-msvc"
                osName.contains("mac") && arch == "x86_64" -> "x86_64-apple-darwin"
                osName.contains("mac") && arch == "aarch64" -> "aarch64-apple-darwin"
                osName.contains("linux") && arch == "aarch64" -> "aarch64-unknown-linux-gnu"
                else -> "x86_64-unknown-linux-gnu" // Default case for Linux x86_64
            }
        }

        private fun downloadFile(
            url: String,
            outputFile: File,
        ) {
            println("🌍 Downloading from: $url")
            URI(url).toURL().openStream().use { input ->
                outputFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }

        private fun extractZip(
            zipFile: File,
            outputDir: File,
            unzipTool: String,
        ) {
            println("📦 Extracting ${zipFile.name} to $outputDir")
            try {
                when (unzipTool) {
                    "tar" ->
                        execOperations.exec {
                            commandLine("tar", "-xf", zipFile.absolutePath, "-C", outputDir.absolutePath)
                        }

                    "unzip" ->
                        execOperations.exec {
                            commandLine("unzip", "-d", outputDir.absolutePath, "-o", zipFile.absolutePath)
                        }

                    "7z", "7zz" ->
                        execOperations.exec {
                            commandLine(unzipTool, "x", "-o$outputDir", "-y", zipFile.absolutePath)
                        }

                    else -> throw IllegalStateException("❌ Unknown unzip tool: $unzipTool")
                }
            } finally {
                zipFile.delete() // Cleanup
            }
        }

        private fun determineUnzipTool(): String =
            when {
                isWindows() -> "tar"
                checkUnixTool("unzip") -> "unzip"
                checkUnixTool("7z") -> "7z"
                checkUnixTool("7zz") -> "7zz"
                else -> throw IllegalStateException(
                    "❌ Either unzip or 7z is required to install Deno (see: https://github.com/denoland/deno_install#either-unzip-or-7z-is-required ).",
                )
            }

        private fun checkUnixTool(tool: String): Boolean =
            execOperations.exec { commandLine("which", tool).setIgnoreExitValue(true) }.exitValue == 0
    }
