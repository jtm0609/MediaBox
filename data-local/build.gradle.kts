plugins {
    id("module.android")
}

android {
    namespace = "com.example.data.local"
}

dependencies {

    implementation(project(":data"))

    implementation(libs.datastore.preferences)
    implementation(libs.datastore.core)
    implementation(libs.datastore.preferences.core)
}