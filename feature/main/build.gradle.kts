plugins {
    id("module.feature")
}

android {
    namespace = "com.example.main"
}

dependencies {
    implementation(project(":feature:bookmark"))
    implementation(project(":feature:search"))
}