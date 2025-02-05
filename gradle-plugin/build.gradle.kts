plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.3.1"
}

group = "io.github.masch0212"
version = "0.0.1"

gradlePlugin {
    plugins {
        create("denoPlugin") {
            id = "io.github.masch0212.deno"
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

publishing {
    repositories {
        mavenCentral()
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = "io.github.masch0212"
            artifactId = "deno-gradle-plugin"
            version = "0.0.1"
        }
    }
}