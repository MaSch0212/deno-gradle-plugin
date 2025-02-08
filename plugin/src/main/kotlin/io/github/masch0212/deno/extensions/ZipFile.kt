package io.github.masch0212.deno.extensions

import java.io.File
import java.util.zip.ZipFile
import org.gradle.internal.cc.base.logger

fun ZipFile.extractAll(destination: File) {
  entries().asSequence().forEach { entry ->
    logger.debug("Extracting entry: {}", entry.name)
    val entryDestination = File(destination, entry.name)
    if (entry.isDirectory) {
      entryDestination.mkdirs()
    } else {
      entryDestination.parentFile.mkdirs()
      getInputStream(entry).use { input ->
        entryDestination.outputStream().use { output -> input.copyTo(output) }
      }
    }
  }
}
