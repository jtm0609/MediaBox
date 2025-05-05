pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "mediaBox"
include(":app")
include(":data")
include(":domain")
include(":data-local")
include(":data-remote")
include(":feature")
include(":feature:main")
include(":feature:bookmark")
include(":feature:search")
include(":core-ui")
