import gradle.kotlin.dsl.accessors._cff335d5bf84659b84df8e174ca985f2.implementation
import org.gradle.api.JavaVersion

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}


val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation(libs.getLibrary("androidx.core.ktx"))
    implementation(libs.getLibrary("androidx.appcompat"))
    implementation(libs.getLibrary("material"))
    implementation(libs.getLibrary("androidx.lifecycle.runtime"))

    implementation(libs.getLibrary("hilt.android"))
    kapt(libs.getLibrary("hilt.android.compiler"))

    implementation(libs.getLibrary("coroutines.android"))
    implementation(libs.getLibrary("kotlinx.serialization.json"))
    implementation(libs.getLibrary("kotlinx.datetime"))
}
