package io.github.masch0212.deno

import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

abstract class DenoExtension @Inject constructor(objects: ObjectFactory) {
  val version: Property<String> = objects.property(String::class.java).convention("latest")
}
