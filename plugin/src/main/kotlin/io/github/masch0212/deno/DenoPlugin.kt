package io.github.masch0212.deno

import io.github.masch0212.deno.services.DenoService
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.cache.internal.scopes.DefaultGlobalScopedCacheBuilderFactory

class DenoPlugin
@Inject
constructor(private val globalCache: DefaultGlobalScopedCacheBuilderFactory) : Plugin<Project> {
  override fun apply(target: Project) {
    // Services
    target.gradle.sharedServices.registerIfAbsent("deno", DenoService::class.java) {
      parameters.cacheRoots = globalCache.globalCacheRoots
    }

    // Extensions
    target.extensions.create("deno", DenoExtension::class.java)

    // Tasks
    target.tasks.register("installDeno", InstallDenoTask::class.java)
  }
}
