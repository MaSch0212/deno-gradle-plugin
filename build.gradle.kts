import io.github.masch0212.deno.RunDenoTask

plugins {
    id("io.github.masch0212.deno")
}

group = "io.github.masch0212"
version = "0.0.1"

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