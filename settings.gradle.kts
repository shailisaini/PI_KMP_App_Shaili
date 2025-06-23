enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev") } // ✅ For Kotlin dev plugins
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("myLibs") {
            from(files("gradle/libs.versions.toml")) // ✅ Only ONE from() call!
        }
    }
}

rootProject.name = "PI_KMP_Application"
include(":androidApp")
include(":shared")