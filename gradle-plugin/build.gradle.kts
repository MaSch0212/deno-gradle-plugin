plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `java-library`
    `maven-publish`
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
//        maven {
//            name = "OSSRH"
//            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
//            credentials {
//                username = System.getenv("MAVEN_USERNAME") ?: project.property("MAVEN_USERNAME").toString()
//                password = System.getenv("MAVEN_PASSWORD") ?: project.property("MAVEN_PASSWORD").toString()
//            }
//        }
        maven {
            url = uri("file://${project.buildDir}/repo")
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = "io.github.masch0212"
            artifactId = "deno-gradle-plugin"
            version = "0.0.1"

            pom {
                name.set("Deno Gradle Plugin")
                description.set("A plugin to install and manage Deno versions")
                url.set("https://github.com/MaSch0212/deno-gradle-plugin")

                scm {
                    url.set("https://github.com/MaSch0212/deno-gradle-plugin")
                    connection.set("scm:git:git://github.com/MaSch0212/deno-gradle-plugin.git")
                    developerConnection.set("scm:git:ssh://github.com/MaSch0212/deno-gradle-plugin.git")
                }

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("MaSch0212")
                        name.set("Marc Schmidt")
                        email.set("code@masch212.de")
                    }
                }
            }
        }
    }
}