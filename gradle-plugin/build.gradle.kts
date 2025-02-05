plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.masch212"
version = "1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        create("denoPlugin") {
            id = "com.masch212.deno"
            implementationClass = "com.masch212.deno.DenoPlugin"
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