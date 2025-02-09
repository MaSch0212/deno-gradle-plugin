package io.github.masch0212.deno.command

interface DenoCommandBuilderWithDebugging<T> {
  /**
   * Activate inspector on host:port. (Default: `127.0.0.1:9229`)
   *
   * @param hostAndPort The host and port to listen on.
   */
  fun inspect(hostAndPort: String? = null): T

  /**
   * Activate inspector on host:port, wait for debugger to connect and break at the start of user
   * script.
   *
   * @param hostAndPort The host and port to listen on.
   */
  fun inspectBrk(hostAndPort: String? = null): T

  /**
   * Activate inspector on host:port and wait for debugger to connect before running user code.
   *
   * @param hostAndPort The host and port to listen on.
   */
  fun inspectWait(hostAndPort: String? = null): T
}

interface DenoCommandBuilderDebuggingComposable<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderWithDebugging<T>, DenoCommandBuilderComposable<T>

class DenoCommandBuilderDebuggingComposableImpl<T : DenoCommandBuilderBase<T>> :
    DenoCommandBuilderDebuggingComposable<T> {
  private lateinit var builder: T

  override fun initialize(builder: T) {
    this.builder = builder
  }

  override fun inspect(hostAndPort: String?) = builder.valueArg("--inspect", hostAndPort)

  override fun inspectBrk(hostAndPort: String?) = builder.valueArg("--inspect-brk", hostAndPort)

  override fun inspectWait(hostAndPort: String?) = builder.valueArg("--inspect-wait", hostAndPort)
}
