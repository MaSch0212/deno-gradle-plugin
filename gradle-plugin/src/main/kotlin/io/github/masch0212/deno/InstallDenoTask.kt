package io.github.masch0212.deno

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.net.URL

open class InstallDenoTask : DefaultTask() {
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

        downloadFile(downloadUrl, zipFile)
        extractZip(zipFile, installDir)

        // Make executable
        denoExecutable.setExecutable(true)

        println("🎉 Deno $denoVersion installed successfully at $installDir")
    }

    private fun fetchLatestDenoVersion(): String {
        println("🔍 Fetching latest Deno version...")
        return URL("https://dl.deno.land/release-latest.txt").readText().trim()
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

    private fun downloadFile(url: String, outputFile: File) {
        println("🌍 Downloading from: $url")
        URL(url).openStream().use { input ->
            outputFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }

    private fun extractZip(zipFile: File, outputDir: File) {
        println("📦 Extracting ${zipFile.name} to $outputDir")
        project.exec {
            commandLine("tar", "-xf", zipFile.absolutePath, "-C", outputDir.absolutePath)
        }
        zipFile.delete() // Cleanup
    }
}