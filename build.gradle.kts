import io.github.masch0212.deno.RunDenoTask

plugins {
  id("io.github.masch0212.deno")
  id("com.ncorti.ktfmt.gradle") version "0.22.0"
}

group = "io.github.masch0212"

version = "0.0.1"

deno { version.set("2.1.7") }

repositories { mavenCentral() }

tasks.register<RunDenoTask>("myTestDenoTask") {
  group = "Deno"
  command = "deno --version"
  dependsOn(tasks.installDeno)
}
