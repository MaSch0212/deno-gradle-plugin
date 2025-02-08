package io.github.masch0212.deno.utils

import java.io.File

enum class DenoTarget(val value: String, val executableFileName: String) {
  LINUX_ARM64("aarch64-unknown-linux-gnu", "deno"),
  LINUX_X86_64("x86_64-unknown-linux-gnu", "deno"),
  MACOS_ARM64("aarch64-apple-darwin", "deno"),
  MACOS_X86_64("x86_64-apple-darwin", "deno"),
  WINDOWS_X86_64("x86_64-pc-windows-msvc", "deno.exe");

  companion object {
    fun fromOsInfo(osInfo: OSInfo) =
        when (osInfo.kind) {
          OSKind.LINUX ->
              when (osInfo.arch) {
                OSArch.X64 -> LINUX_X86_64
                OSArch.ARM -> LINUX_ARM64
                else -> throw IllegalArgumentException("Unsupported architecture: ${osInfo.arch}")
              }
          OSKind.MACOS ->
              when (osInfo.arch) {
                OSArch.X64 -> MACOS_X86_64
                OSArch.ARM -> MACOS_ARM64
                else -> throw IllegalArgumentException("Unsupported architecture: ${osInfo.arch}")
              }
          OSKind.WINDOWS -> WINDOWS_X86_64
          OSKind.UNKNOWN -> throw IllegalArgumentException("Unsupported OS: ${osInfo.name}")
        }
  }
}

data class Deno(val version: String, val executable: File, val target: DenoTarget)
