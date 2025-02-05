import com.masch212.deno.RunDenoTask

plugins {
//    kotlin("jvm") version "1.8.0"
    id("com.masch212.deno")
}

group = "com.masch212"
version = "1.0-SNAPSHOT"

deno {
    version.set("2.1.8")
}

repositories {
    mavenCentral()
}

tasks.register<RunDenoTask>("myTestDenoTask") {
    group = "Deno"
    command = "deno --version"
    dependsOn(tasks.installDeno)
}