plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
//    `java-library`
//    `maven-publish`
//    `signing`
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


//val generateJavadoc by tasks.registering(Jar::class) {
//    dependsOn(tasks.javadoc)
//    from(tasks.javadoc.get().destinationDir)
//    archiveClassifier.set("javadoc") // This will create a JAR with the 'javadoc' suffix
//}
//
//val generateSources by tasks.registering(Jar::class) {
//    dependsOn(tasks.classes)
//    from(sourceSets["main"].allJava)
//    archiveClassifier.set("sources") // This will create a JAR with the 'sources' suffix
//}
//
//publishing {
//    repositories {
////        maven {
////            name = "OSSRH"
////            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
////            credentials {
////                username = System.getenv("MAVEN_USERNAME") ?: project.property("MAVEN_USERNAME").toString()
////                password = System.getenv("MAVEN_PASSWORD") ?: project.property("MAVEN_PASSWORD").toString()
////            }
////        }
//        maven {
//            url = uri("file://${project.buildDir}/repo")
//        }
//    }
//    publications {
//        create<MavenPublication>("maven") {
//            from(components["java"])
//            groupId = "io.github.masch0212"
//            artifactId = "deno-gradle-plugin"
//            version = "0.0.1"
//
//            artifact(generateJavadoc.get())
//            artifact(generateSources.get())
//
//            pom {
//                name.set("Deno Gradle Plugin")
//                description.set("A plugin to install and manage Deno versions")
//                url.set("https://github.com/MaSch0212/deno-gradle-plugin")
//
//                scm {
//                    url.set("https://github.com/MaSch0212/deno-gradle-plugin")
//                    connection.set("scm:git:git://github.com/MaSch0212/deno-gradle-plugin.git")
//                    developerConnection.set("scm:git:ssh://github.com/MaSch0212/deno-gradle-plugin.git")
//                }
//
//                licenses {
//                    license {
//                        name.set("MIT License")
//                        url.set("https://opensource.org/licenses/MIT")
//                    }
//                }
//
//                developers {
//                    developer {
//                        id.set("MaSch0212")
//                        name.set("Marc Schmidt")
//                        email.set("code@masch212.de")
//                    }
//                }
//            }
//        }
//    }
//}

//signing {
//    useGpgCmd()
//    sign(publishing.publications["maven"])
//}
