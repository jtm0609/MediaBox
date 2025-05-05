plugins {
    id("module.feature")
}

android {
    namespace = "com.example.search"
}

dependencies {
    implementation(libs.paging.common.android)
    implementation(libs.paging.compose)
}