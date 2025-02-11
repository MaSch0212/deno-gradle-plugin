plugins { id("io.github.masch0212.deno") apply false }

allprojects {
  if (project.name != "deno-gradle-plugin") {
    plugins.apply("io.github.masch0212.deno")
  }
}
