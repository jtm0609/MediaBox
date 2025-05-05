plugins {
    id("module.android")
}

android {
    namespace = "com.example.data.remote"

    defaultConfig {
        buildConfigField("String", "REST_API_KEY", "\"KakaoAK b5702c968b4c29e6b1a45dbce386f6d9\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":data"))

    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.retrofit.kotlin.serialization)
}