import gradle.kotlin.dsl.accessors._872ad73bda21390b99a23da2f9228964.implementation
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

plugins {
    id("module.android")
    id("module.compose")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation(project(":domain"))
    implementation(project(":core-ui"))

    implementation(libs.getLibrary("androidx.lifecycle.viewmodel"))
    implementation(libs.getLibrary("viewmodel.compose"))
    implementation(libs.getLibrary("kotlinx.collections.immutable"))
    implementation(libs.getLibrary("navigation.compose"))
    implementation(libs.getLibrary("hilt.navigation.compose"))
}