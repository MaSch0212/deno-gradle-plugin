plugins {
  kotlin("jvm") version "2.0.20"
  `kotlin-dsl`
  `java-gradle-plugin`
  id("com.gradle.plugin-publish") version "1.3.1"
  id("com.ncorti.ktfmt.gradle") version "0.22.0"
}

group = "io.github.masch0212"

version = "0.1.0"

kotlin { jvmToolchain(21) }

repositories { mavenCentral() }

dependencies {
  //    testImplementation(kotlin("test"))
  implementation(gradleApi())
  implementation(localGroovy())
}

gradlePlugin {
  website = "https://github.com/MaSch0212/deno-gradle-plugin"
  vcsUrl = "https://github.com/MaSch0212/deno-gradle-plugin"

  plugins {
    create("denoPlugin") {
      id = "io.github.masch0212.deno"
      displayName = "Deno Gradle Plugin"
      description = "A plugin to install and manage Deno versions"
      tags = listOf("deno")
      implementationClass = "io.github.masch0212.deno.DenoPlugin"
    }
  }
}

// tasks.test {
//    useJUnitPlatform()
// }

// kotlin {
//    jvmToolchain(8)
// }
