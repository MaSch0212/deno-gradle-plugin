plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0" }

rootProject.name = "deno-gradle-plugin"

includeBuild("plugin")

include("samples")
