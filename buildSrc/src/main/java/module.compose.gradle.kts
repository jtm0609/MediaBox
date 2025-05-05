import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

android {
    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.getVersion("composeCompiler").toString()
    }
}

dependencies {
    implementation(platform(libs.getLibrary("compose.bom")))

    implementation(libs.getLibrary("compose.runtime"))
    implementation(libs.getLibrary("compose.material3"))
    implementation(libs.getLibrary("compose.material3.android"))
    implementation(libs.getLibrary("compose.ui"))
    implementation(libs.getLibrary("compose.ui.graphics"))
    implementation(libs.getLibrary("compose.ui.tooling.preview"))
    implementation(libs.getLibrary("activity.compose"))
}