import io.github.masch0212.deno.RunDenoTask

plugins {
  id("io.github.masch0212.deno")
  id("com.ncorti.ktfmt.gradle") version "0.22.0"
}

deno { version.set("2.1.9") }

tasks.register<RunDenoTask>("myTestDenoTask") {
  dependsOn(tasks.installDeno)
  group = "Deno"
  command("--version")
  run("D:\\temp\\deno test.ts", "--arg-1") {
    allowAll()
    scriptArgs("--arg-2")
  }

  /**
   * Commands:
   * - run ✅
   * - serve ✅
   * - task ✅
   * - repl ❌
   * - eval ✅
   * - add ❌
   * - install ✅
   * - uninstall ❌
   * - outdated ❌
   * - remove ❌
   * - bench ❌
   * - check ✅
   * - clean ❌
   * - compile ⏳
   * - coverage ⏳
   * - doc ⏳
   * - fmt ⏳
   * - info ❌
   * - jupyter ❌
   * - lint ⏳
   * - init ❌
   * - test ⏳
   * - publish ⏳
   * - upgrade ❌
   */
}
