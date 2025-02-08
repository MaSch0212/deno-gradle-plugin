package io.github.masch0212.deno.utils

enum class OSKind {
  WINDOWS,
  LINUX,
  MACOS,
  UNKNOWN
}

enum class OSArch {
  X86,
  X64,
  ARM,
  UNKNOWN
}

data class OSInfo(val name: String, val archName: String) {
  companion object {
    val CURRENT by lazy { OSInfo(System.getProperty("os.name"), System.getProperty("os.arch")) }
  }

  val kind: OSKind =
      when {
        name.lowercase().contains("win") -> OSKind.WINDOWS
        name.lowercase().contains("nux") -> OSKind.LINUX
        name.lowercase().contains("mac") -> OSKind.MACOS
        else -> OSKind.UNKNOWN
      }

  val arch: OSArch =
      when (archName.lowercase()) {
        "aarch64" -> OSArch.ARM
        "x86-64",
        "amd64" -> OSArch.X64
        "x86" -> OSArch.X86
        else -> OSArch.UNKNOWN
      }
}
