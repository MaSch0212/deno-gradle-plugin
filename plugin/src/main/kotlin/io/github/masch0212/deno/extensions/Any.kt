package io.github.masch0212.deno.extensions

fun <T> T.apply(block: (T.() -> Unit)?): T {
  block?.invoke(this)
  return this
}
