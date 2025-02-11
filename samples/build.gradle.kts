import io.github.masch0212.deno.InstallDenoTask
import io.github.masch0212.deno.RunDenoTask

plugins { id("com.ncorti.ktfmt.gradle") version "0.22.0" }

deno { version.set("2.1.9") }

tasks.register<RunDenoTask>("myTestDenoTask") {
  dependsOn(tasks.installDeno)
  group = "Deno"
  command("--version")
  run("scripts/deno test.ts", "--arg-1") {
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

val installDeno2_1_8 = tasks.register<InstallDenoTask>("installDeno2_1_8") { version = "2.1.8" }

tasks.register<RunDenoTask>("runDeno2_1_8") {
  dependsOn(installDeno2_1_8)
  group = "Deno"
  version = "2.1.8"
  command("--version")
}
