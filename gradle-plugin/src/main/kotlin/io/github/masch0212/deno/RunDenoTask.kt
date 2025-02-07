package io.github.masch0212.deno

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

open class RunDenoTask
    @Inject
    constructor(
        private val execOperations: ExecOperations,
    ) : DefaultTask() {
        companion object {
            private val COMMAND_PART_REGEX = Pattern.compile("""[^\s"']+|"(.*?)"|'(.*?)'""")
        }

        @Input
        @Optional
        var version: String =
            project.extensions
                .getByType(DenoExtension::class.java)
                .version
                .get()

        @Input
        var command: String = "--version"

        @TaskAction
        fun runCommand() {
            val cacheDir = File(project.gradle.gradleUserHomeDir, "caches/deno")
            val denoVersion =
                when {
                    version == "latest" -> fetchLatestInstalledVersion(cacheDir)
                    !version.startsWith("v") -> "v$version"
                    else -> version
                }
            val installDir = File(cacheDir, denoVersion)
            val denoExecutable = File(installDir, "deno${if (isWindows()) ".exe" else ""}")

            if (!denoExecutable.exists()) {
                throw IllegalStateException("❌ Deno $denoVersion is not installed. Run `./gradlew installDeno` first.")
            }

            val command =
                when {
                    command.startsWith("deno ") -> command.substring(5)
                    command.startsWith("deno.exe ") -> command.substring(9)
                    else -> command
                }

            println("🚀 Running: ${denoExecutable.name} $command")
            val commandParts = parseCommand(command)
            execOperations.exec {
                commandLine(denoExecutable.absolutePath, *commandParts.toTypedArray())
            }
        }

        private fun fetchLatestInstalledVersion(cacheDir: File): String =
            cacheDir.list()?.maxOrNull()
                ?: throw IllegalStateException("❌ No installed Deno versions found!")

        /**
         * Parses a shell-like command string into separate arguments, respecting quotes.
         * Supports both single (' ') and double (" ") quotes.
         */
        private fun parseCommand(command: String): List<String> {
            val regex = Pattern.compile("""[^\s"']+|"(.*?)"|'(.*?)'""")
            val matcher: Matcher = regex.matcher(command)
            val result = mutableListOf<String>()

            while (matcher.find()) {
                val quoted = matcher.group(1) ?: matcher.group(2) // Capture inside quotes
                result.add(quoted ?: matcher.group()) // Use quoted content if found
            }

            return result
        }
    }
