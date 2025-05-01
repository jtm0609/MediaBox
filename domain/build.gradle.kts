plugins {
    alias(libs.plugins.java.library)
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(libs.hilt.core)
    implementation(libs.androidx.paging.common.android)

    kapt(libs.hilt.compiler)
    implementation(libs.coroutines.core)

    implementation ("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
}
