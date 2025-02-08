package io.github.masch0212.deno.command

interface DenoCommandBuilderComposable<T : DenoCommandBuilderBase<T>> {
  fun initialize(builder: T)
}
