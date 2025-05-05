import org.gradle.api.JavaVersion
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation(libs.getLibrary("coroutines.core"))
    implementation(libs.getLibrary("kotlinx.serialization.json"))
    implementation(libs.getLibrary("kotlinx.datetime"))

    implementation(libs.getLibrary("hilt.core"))
    kapt(libs.getLibrary("hilt.compiler"))
}