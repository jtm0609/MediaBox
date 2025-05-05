plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}
repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}
tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

dependencies {
    implementation(libs.gradle)
    implementation(libs.kotlin)
    implementation(libs.kotlin.compiler.embeddable)
    implementation(libs.hilt.gradle)
    implementation(libs.kotlin.serialization)
    implementation(libs.compose.compiler.gradle.plugin)
}