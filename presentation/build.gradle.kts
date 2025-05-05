plugins {
    id("module.android")
    id("module.compose")
}

android {
    namespace = "com.example.presentation"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel)

    implementation(libs.paging.common.android)
    implementation(libs.paging.compose)

    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.hilt.navigation.compose)
    implementation(libs.navigation.compose)

    implementation(libs.coil.compose)
}