plugins {
    id("module.android")
    id("module.compose")
}

android {
    namespace = "com.example.core_ui"
}

dependencies {
    implementation(libs.coil.compose)
}