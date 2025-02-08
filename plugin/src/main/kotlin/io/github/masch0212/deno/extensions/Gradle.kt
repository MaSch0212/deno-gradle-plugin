package io.github.masch0212.deno.extensions

import io.github.masch0212.deno.DenoExtension
import org.gradle.api.DefaultTask
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

internal inline fun <reified T> ObjectFactory.property(convention: T): Property<T> =
    property(T::class.java).convention(convention)

internal inline fun <reified T> ObjectFactory.listProperty(convention: List<T>?): ListProperty<T?> =
    listProperty(T::class.java).convention(convention)

internal inline fun <reified T> DefaultTask.denoProperty(
    block: DenoExtension.() -> Property<T>
): T =
    block(project.extensions.getByType(DenoExtension::class.java)).let { property ->
      if (null is T) property.orNull as T else property.get()
    }

internal fun <T> DefaultTask.denoListProperty(
    block: DenoExtension.() -> ListProperty<T>
): List<T>? = block(project.extensions.getByType(DenoExtension::class.java)).orNull
