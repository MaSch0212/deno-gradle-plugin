package io.github.masch0212.deno

internal fun isWindows(): Boolean {
    return System.getProperty("os.name").lowercase().contains("win")
}