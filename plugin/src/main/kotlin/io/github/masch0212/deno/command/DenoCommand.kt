package io.github.masch0212.deno.command

import io.github.masch0212.deno.extensions.combine
import io.github.masch0212.deno.extensions.quoteIfNecessary
import io.github.masch0212.deno.utils.DenoTarget
import io.github.masch0212.deno.utils.OSInfo

data class DenoCommand(
    val args: List<String>,
    val env: Map<String, String>,
    val workingDir: String?
) {
  override fun toString() = toString(DenoTarget.fromOsInfo(OSInfo.CURRENT))

  fun toString(
      target: DenoTarget,
      includeEnv: Boolean = false,
      additionalEnv: Map<String, String> = emptyMap()
  ) =
      combine(
          if (includeEnv)
              env.plus(additionalEnv).entries.joinToString(" ") {
                "${it.key}=${it.value.quoteIfNecessary()}"
              }
          else null,
          target.executableFileName,
          args.joinToString(" ") { it.quoteIfNecessary() })
}
