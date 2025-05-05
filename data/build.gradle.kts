plugins {
    id("module.android")
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.paging.runtime)
}