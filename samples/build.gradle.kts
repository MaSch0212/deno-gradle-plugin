import io.github.masch0212.deno.RunDenoTask

plugins {
  id("io.github.masch0212.deno")
  id("com.ncorti.ktfmt.gradle") version "0.22.0"
}

// deno { version.set("2.1.9") }

tasks.register<RunDenoTask>("myTestDenoTask") {
  dependsOn(tasks.installDeno)
  group = "Deno"
  command("--version")
  run("D:\\temp\\deno test.ts", "--arg-1") {
    allowAll()
    scriptArgs("--arg-2")
  }
}
