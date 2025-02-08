package io.github.masch0212.deno.command

interface DenoCommandBuilderWithSecurity<T> {
  /** Allow all permissions. */
  fun allowAll(): T

  /** Always throw if required permission wasn't passed. */
  fun noPrompt(): T

  /**
   * Allow file system read access. Optionally specify allowed paths.
   *
   * @param allowedPaths Allowed paths. If empty, all paths are allowed.
   */
  fun allowRead(vararg allowedPaths: String): T

  /**
   * Allow file system read access. Optionally specify allowed paths.
   *
   * @param allowedPaths Allowed paths. If empty, all paths are allowed.
   */
  fun allowRead(allowedPaths: Iterable<String>): T

  /**
   * Allow file system write access. Optionally specify allowed paths.
   *
   * @param allowedPaths Allowed paths. If empty, all paths are allowed.
   */
  fun allowWrite(vararg allowedPaths: String): T

  /**
   * Allow file system write access. Optionally specify allowed paths.
   *
   * @param allowedPaths Allowed paths. If empty, all paths are allowed.
   */
  fun allowWrite(allowedPaths: Iterable<String>): T

  /**
   * Allow importing from remote hosts. Optionally specify allowed IP addresses and host names, with
   * ports as necessary.
   *
   * @param allowedIpsOrHostnames Allowed IP addresses and host names. If empty, the default is:
   *   deno.land:443,jsr.io:443,esm.sh:443,cdn.jsdelivr.net:443,raw.githubusercontent.com:443,user.githubusercontent.com:443
   */
  fun allowImport(vararg allowedIpsOrHostnames: String): T

  /**
   * Allow importing from remote hosts. Optionally specify allowed IP addresses and host names, with
   * ports as necessary.
   *
   * @param allowedIpsOrHostnames Allowed IP addresses and host names. If empty, the default is:
   *   deno.land:443,jsr.io:443,esm.sh:443,cdn.jsdelivr.net:443,raw.githubusercontent.com:443,user.githubusercontent.com:443
   */
  fun allowImport(allowedIpsOrHostnames: Iterable<String>): T

  /**
   * Allow network access. Optionally specify allowed IP addresses and host names, with ports as
   * necessary.
   *
   * @param allowedIpsOrHostnames Allowed IP addresses and host names. If empty, all hosts are
   *   allowed.
   */
  fun allowNet(vararg allowedIpsOrHostnames: String): T

  /**
   * Allow network access. Optionally specify allowed IP addresses and host names, with ports as
   * necessary.
   *
   * @param allowedIpsOrHostnames Allowed IP addresses and host names. If empty, all hosts are
   *   allowed.
   */
  fun allowNet(allowedIpsOrHostnames: Iterable<String>): T

  /**
   * Allow access to environment variables. Optionally specify accessible environment variables.
   *
   * @param allowedEnvVars Allowed environment variables. If empty, all environment variables are
   *   allowed.
   */
  fun allowEnv(vararg allowedEnvVars: String): T

  /**
   * Allow access to environment variables. Optionally specify accessible environment variables.
   *
   * @param allowedEnvVars Allowed environment variables. If empty, all environment variables are
   *   allowed.
   */
  fun allowEnv(allowedEnvVars: Iterable<String>): T

  /**
   * Allow access to OS information. Optionally allow specific APIs by function name.
   *
   * @param allowedSysCalls Allowed APIs by function name. If empty, all APIs is allowed.
   */
  fun allowSys(vararg allowedSysCalls: String): T

  /**
   * Allow access to OS information. Optionally allow specific APIs by function name.
   *
   * @param allowedSysCalls Allowed APIs by function name. If empty, all APIs is allowed.
   */
  fun allowSys(allowedSysCalls: Iterable<String>): T

  /**
   * Allow running subprocesses. Optionally specify allowed runnable program names.
   *
   * @param allowedProgramNames Allowed runnable program names. If empty, all programs are allowed.
   */
  fun allowRun(vararg allowedProgramNames: String): T

  /**
   * Allow running subprocesses. Optionally specify allowed runnable program names.
   *
   * @param allowedProgramNames Allowed runnable program names. If empty, all programs are allowed.
   */
  fun allowRun(allowedProgramNames: Iterable<String>): T

  /**
   * (Unstable) Allow loading dynamic libraries. Optionally specify allowed directories or files.
   *
   * @param allowedPaths Allowed directories or files. If empty, all directories and files are
   *   allowed.
   */
  fun allowFfi(vararg allowedPaths: String): T

  /**
   * (Unstable) Allow loading dynamic libraries. Optionally specify allowed directories or files.
   *
   * @param allowedPaths Allowed directories or files. If empty, all directories and files are
   *   allowed.
   */
  fun allowFfi(allowedPaths: Iterable<String>): T

  /**
   * Deny file system read access. Optionally specify denied paths.
   *
   * @param deniedPaths Denied paths. If empty, all paths are denied.
   */
  fun denyRead(vararg deniedPaths: String): T

  /**
   * Deny file system read access. Optionally specify denied paths.
   *
   * @param deniedPaths Denied paths. If empty, all paths are denied.
   */
  fun denyRead(deniedPaths: Iterable<String>): T

  /**
   * Deny file system write access. Optionally specify denied paths.
   *
   * @param deniedPaths Denied paths. If empty, all paths are denied.
   */
  fun denyWrite(vararg deniedPaths: String): T

  /**
   * Deny file system write access. Optionally specify denied paths.
   *
   * @param deniedPaths Denied paths. If empty, all paths are denied.
   */
  fun denyWrite(deniedPaths: Iterable<String>): T

  /**
   * Deny network access. Optionally specify defined IP addresses and host names, with ports as
   * necessary.
   *
   * @param deniedIpsOrHostnames Denied IP addresses and host names. If empty, all hosts are denied.
   */
  fun denyNet(vararg deniedIpsOrHostnames: String): T

  /**
   * Deny network access. Optionally specify defined IP addresses and host names, with ports as
   * necessary.
   *
   * @param deniedIpsOrHostnames Denied IP addresses and host names. If empty, all hosts are denied.
   */
  fun denyNet(deniedIpsOrHostnames: Iterable<String>): T

  /**
   * Deny access to environment variables. Optionally specify denied environment variables.
   *
   * @param deniedEnvVars Denied environment variables. If empty, all environment variables are
   *   denied.
   */
  fun denyEnv(vararg deniedEnvVars: String): T

  /**
   * Deny access to environment variables. Optionally specify denied environment variables.
   *
   * @param deniedEnvVars Denied environment variables. If empty, all environment variables are
   *   denied.
   */
  fun denyEnv(deniedEnvVars: Iterable<String>): T

  /**
   * Deny access to OS information. Optionally deny specific APIs by function name.
   *
   * @param deniedSysCalls Denied APIs by function name. If empty, all APIs are denied.
   */
  fun denySys(vararg deniedSysCalls: String): T

  /**
   * Deny access to OS information. Optionally deny specific APIs by function name.
   *
   * @param deniedSysCalls Denied APIs by function name. If empty, all APIs are denied.
   */
  fun denySys(deniedSysCalls: Iterable<String>): T

  /**
   * Deny running subprocesses. Optionally specify denied runnable program names.
   *
   * @param deniedProgramNames Denied runnable program names. If empty, all programs are denied.
   */
  fun denyRun(vararg deniedProgramNames: String): T

  /**
   * Deny running subprocesses. Optionally specify denied runnable program names.
   *
   * @param deniedProgramNames Denied runnable program names. If empty, all programs are denied.
   */
  fun denyRun(deniedProgramNames: Iterable<String>): T

  /**
   * (Unstable) Deny loading dynamic libraries. Optionally specify denied directories or files.
   *
   * @param deniedPaths Denied directories or files. If empty, all directories and files are denied.
   */
  fun denyFfi(vararg deniedPaths: String): T

  /**
   * (Unstable) Deny loading dynamic libraries. Optionally specify denied directories or files.
   *
   * @param deniedPaths Denied directories or files. If empty, all directories and files are denied.
   */
  fun denyFfi(deniedPaths: Iterable<String>): T

  /**
   * Enable or disable stack traces in permission prompts.
   *
   * @param enabled Whether to enable stack traces in permission prompts.
   */
  fun withPermissionTracing(enabled: Boolean = true): T
}

interface DenoCommandBuilderSecurityComposable<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderWithSecurity<T>, DenoCommandBuilderComposable<T>

internal class DenoCommandBuilderSecurityComposableImpl<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderSecurityComposable<T> {
  private lateinit var builder: T

  override fun initialize(builder: T) {
    this.builder = builder
  }

  override fun allowAll() = builder.args("--allow-all")

  override fun noPrompt() = builder.args("--no-prompt")

  override fun allowRead(vararg allowedPaths: String) =
      builder.valueArg("--allow-read", allowedPaths.toList())

  override fun allowRead(allowedPaths: Iterable<String>) =
      builder.valueArg("--allow-read", allowedPaths.toList())

  override fun allowWrite(vararg allowedPaths: String) =
      builder.valueArg("--allow-write", allowedPaths.toList())

  override fun allowWrite(allowedPaths: Iterable<String>) =
      builder.valueArg("--allow-write", allowedPaths.toList())

  override fun allowImport(vararg allowedIpsOrHostnames: String) =
      builder.valueArg("--allow-import", allowedIpsOrHostnames.toList())

  override fun allowImport(allowedIpsOrHostnames: Iterable<String>) =
      builder.valueArg("--allow-import", allowedIpsOrHostnames.toList())

  override fun allowNet(vararg allowedIpsOrHostnames: String) =
      builder.valueArg("--allow-net", allowedIpsOrHostnames.toList())

  override fun allowNet(allowedIpsOrHostnames: Iterable<String>) =
      builder.valueArg("--allow-net", allowedIpsOrHostnames.toList())

  override fun allowEnv(vararg allowedEnvVars: String) =
      builder.valueArg("--allow-env", allowedEnvVars.toList())

  override fun allowEnv(allowedEnvVars: Iterable<String>) =
      builder.valueArg("--allow-env", allowedEnvVars.toList())

  override fun allowSys(vararg allowedSysCalls: String) =
      builder.valueArg("--allow-sys", allowedSysCalls.toList())

  override fun allowSys(allowedSysCalls: Iterable<String>) =
      builder.valueArg("--allow-sys", allowedSysCalls.toList())

  override fun allowRun(vararg allowedProgramNames: String) =
      builder.valueArg("--allow-run", allowedProgramNames.toList())

  override fun allowRun(allowedProgramNames: Iterable<String>) =
      builder.valueArg("--allow-run", allowedProgramNames.toList())

  override fun allowFfi(vararg allowedPaths: String) =
      builder.valueArg("--allow-ffi", allowedPaths.toList())

  override fun allowFfi(allowedPaths: Iterable<String>) =
      builder.valueArg("--allow-ffi", allowedPaths.toList())

  override fun denyRead(vararg deniedPaths: String) =
      builder.valueArg("--deny-read", deniedPaths.toList())

  override fun denyRead(deniedPaths: Iterable<String>) =
      builder.valueArg("--deny-read", deniedPaths.toList())

  override fun denyWrite(vararg deniedPaths: String) =
      builder.valueArg("--deny-write", deniedPaths.toList())

  override fun denyWrite(deniedPaths: Iterable<String>) =
      builder.valueArg("--deny-write", deniedPaths.toList())

  override fun denyNet(vararg deniedIpsOrHostnames: String) =
      builder.valueArg("--deny-net", deniedIpsOrHostnames.toList())

  override fun denyNet(deniedIpsOrHostnames: Iterable<String>) =
      builder.valueArg("--deny-net", deniedIpsOrHostnames.toList())

  override fun denyEnv(vararg deniedEnvVars: String) =
      builder.valueArg("--deny-env", deniedEnvVars.toList())

  override fun denyEnv(deniedEnvVars: Iterable<String>) =
      builder.valueArg("--deny-env", deniedEnvVars.toList())

  override fun denySys(vararg deniedSysCalls: String) =
      builder.valueArg("--deny-sys", deniedSysCalls.toList())

  override fun denySys(deniedSysCalls: Iterable<String>) =
      builder.valueArg("--deny-sys", deniedSysCalls.toList())

  override fun denyRun(vararg deniedProgramNames: String) =
      builder.valueArg("--deny-run", deniedProgramNames.toList())

  override fun denyRun(deniedProgramNames: Iterable<String>) =
      builder.valueArg("--deny-run", deniedProgramNames.toList())

  override fun denyFfi(vararg deniedPaths: String) =
      builder.valueArg("--deny-ffi", deniedPaths.toList())

  override fun denyFfi(deniedPaths: Iterable<String>) =
      builder.valueArg("--deny-ffi", deniedPaths.toList())

  override fun withPermissionTracing(enabled: Boolean) =
      builder.environment("DENO_TRACE_PERMISSIONS", if (enabled) "1" else "0")
}
