package com.masch212.deno

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class DenoExtension @Inject constructor(objects: ObjectFactory) {
    val version: Property<String> = objects.property(String::class.java).convention("latest")
}