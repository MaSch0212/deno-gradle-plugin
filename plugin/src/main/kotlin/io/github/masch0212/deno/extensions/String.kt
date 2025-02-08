package io.github.masch0212.deno.extensions

fun String.toIntOrDefault(default: Int) = if (isNotEmpty()) toInt() else default

fun combine(vararg strings: String?) = strings.filter { !it.isNullOrEmpty() }.joinToString(" ")

fun String.quoteIfNecessary() = if (contains(" ")) "\"${replace("\"", "\\\"")}\"" else this
