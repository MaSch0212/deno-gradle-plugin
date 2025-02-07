plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.3.1"
}

group = "io.github.masch0212"
version = "0.0.1"

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

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation(kotlin("test"))
    implementation(gradleApi())
    implementation(localGroovy())
}

//tasks.test {
//    useJUnitPlatform()
//}

//kotlin {
//    jvmToolchain(8)
//}
