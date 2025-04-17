# Deno Gradle Plugin

[![GitHub License](https://img.shields.io/github/license/MaSch0212/deno-gradle-plugin)](LICENSE)
<br>
[![Gradle Plugin Portal Version](https://img.shields.io/gradle-plugin-portal/v/io.github.masch0212.deno?logo=gradle&label=io.github.masch0212.deno)](https://plugins.gradle.org/plugin/io.github.masch0212.deno)

This Gradle plugin provides tasks to install Deno and run Deno commands.

## Motivation 💥

Deno is a simple, modern and secure runtime for JavaScript and TypeScript that uses V8 and is built in Rust.
It is a great alternative to Node.js, and it is gaining popularity in the JavaScript community.

One key advantage of Deno is that it comes with TypeScript support out of the box and is very easy and fast to install.
This plugin aims to make it even easier to use Deno in your Gradle projects.

## Features 🔥

✅ **Install Deno**: Download and install Deno on your machine.

✅ **Run Deno commands**: Run Deno commands in your project.<br>
<span style="display: inline-block; padding-left: 3rem;">
Use the fluent API for the following Deno sub-commands: `run`, `serve`, `task`, `eval`, `install`, `check` _(more
coming)_
</span>

✅ **Version management**: Easily switch between different versions of Deno.

## Getting Started 🚀

Add the plugin to your Gradle project and use the tasks as follows:

### Kotlin DSL

```kotlin
import io.github.masch0212.deno.InstallDenoTask
import io.github.masch0212.deno.RunDenoTask

plugins {
  id("io.github.masch0212.deno")
}

// You can specify the version of Deno to install. By default, the latest version is installed.
deno {
  version.set("2.1.9")
}

// Register a task to run one or more Deno commands.
tasks.register<RunDenoTask>("myTestDenoTask") {
  // Important: The task depends on the `install` task to ensure that Deno is installed before running the commands.
  dependsOn(tasks.installDeno)

  // Run an arbitrary Deno command.
  command("--version")

  // Special methods exist for common Deno commands. For example to run a script:
  run("scripts/deno test.ts", "--arg-1") {
    allowAll()
  }

  // ... or to run a task (requires a deno.json):
  task("test")
}

// If you need different deno versions, simply specify another installDeno command:
val installDeno2_1_8 = tasks.register<InstallDenoTask>("installDeno2_1_8") {
  version = "2.1.8"
}

// Now add a RunDenoTask task for that version:
tasks.register<RunDenoTask>("myTestDenoTask2_1_8") {
  dependsOn(installDeno2_1_8)
  version = "2.1.8"
  command("--version")
}
```

### Groovy DSL

```groovy
import io.github.masch0212.deno.InstallDenoTask
import io.github.masch0212.deno.RunDenoTask

plugins {
  id 'io.github.masch0212.deno'
}

// You can specify the version of Deno to install. By default, the latest version is installed.
deno {
  version = "2.1.9"
}

// Register a task to run one or more Deno commands.
tasks.register("myTestDenoTask", RunDenoTask) {
  // Important: The task depends on the `install` task to ensure that Deno is installed before running the commands.
  dependsOn(tasks.installDeno)

  // Run an arbitrary Deno command.
  it.command {
    it.args("--version")
  }

  // Special methods exist for common Deno commands. For example to run a script:
  it.run("scripts/deno test.ts", ["--arg-1"]) {
    it.allowAll()
    it.scriptArgs("--arg-2")
  }

  // ... or to run a task (requires a deno.json):
  it.task("test", []) {}
}

// If you need different deno versions, simply specify another installDeno command:
def installDeno2_1_8 = tasks.register("installDeno2_1_8", InstallDenoTask) {
  version = "2.1.8"
}

// Now add a RunDenoTask task for that version:
tasks.register("runDeno2_1_8", RunDenoTask) {
  dependsOn(installDeno2_1_8)
  version = "2.1.8"
  it.command {
    it.args("--version")
  }
}
```

## Contributing 🧑🏻‍💻

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also
simply open an issue with the tag "enhancement". Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Development 🧑‍💻

To contribute to the project, make sure you have the following prerequisites installed:

- [**Git**](https://git-scm.com/)
- [**JDK 21**](https://adoptium.net/temurin/releases/?version=21)
- [**IntelliJ IDEA**](https://www.jetbrains.com/idea/download/)
  - Install the [`ktfmt`](https://github.com/facebook/ktfmt) plugin for Kotlin code formatting.

To set up the project for local development, follow these steps:

1. Clone the repository (or your fork) using Git:
   ```bash
   git clone https://github.com/MaSch0212/deno-gradle-plugin.git
   ```
2. Open the project in IntelliJ IDEA.

## License 🔑

Distributed under the MIT License. See [`LICENSE`](LICENSE) for more information.
