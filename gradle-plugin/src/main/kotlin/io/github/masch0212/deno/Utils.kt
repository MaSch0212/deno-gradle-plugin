package io.github.masch0212.deno

internal fun isWindows(): Boolean = System.getProperty("os.name").lowercase().contains("win")
