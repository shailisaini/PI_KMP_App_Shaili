plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.pi.ProjectInclusion.android"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.pi.ProjectInclusion.android"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE",
                "META-INF/NOTICE.md",
                "META-INF/NOTICE",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1",
                "META-INF/*.kotlin_module"
            )
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
//    implementation(libs.bundles.app.ui)
//    implementation(libs.multiplatformSettings.common)
//    implementation(libs.kotlinx.dateTime)
    implementation(libs.koin.androidx.navigation) // if using Navigation
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime.compose)
//    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Jetpack Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Koin for Compose
    implementation(libs.koin.androidx.compose)

    // Usual Ktor, Koin, and Compose dependencies
    // This makes shared code accessible in androidApp
    implementation(project(":shared"))

    debugImplementation(libs.compose.ui.tooling)
//    coreLibraryDesugaring(libs.android.desugaring)
//    testImplementation(libs.junit)

    // image dependencies
    implementation(libs.coil.compose) // Or latest version
    implementation(libs.coil.svg) // For SVG support
}