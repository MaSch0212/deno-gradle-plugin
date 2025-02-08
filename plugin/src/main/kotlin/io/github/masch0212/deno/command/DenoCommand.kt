package io.github.masch0212.deno.command

import io.github.masch0212.deno.extensions.combine
import io.github.masch0212.deno.extensions.quoteIfNecessary
import io.github.masch0212.deno.utils.DenoTarget
import io.github.masch0212.deno.utils.OSInfo

class DenoCommand(val args: List<String>, val env: Map<String, String>) {
  override fun toString() = toString(DenoTarget.fromOsInfo(OSInfo.CURRENT))

  fun toString(target: DenoTarget) =
      combine(
          env.entries.joinToString(" ") { (key, value) -> "$key=${value.quoteIfNecessary()}" },
          target.executableFileName,
          args.joinToString(" ") { it.quoteIfNecessary() })
}
