plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.converter.gson)
            implementation(libs.gson)
            implementation(libs.koin.androidx.compose)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.navigation) // if using Navigation
            implementation(libs.androidx.ui.text)
            implementation(libs.androidx.ui.text.android)
//            implementation(libs.androidx.material3.android)

            implementation(libs.androidx.activity.compose)
//            implementation(libs.compose.runtime)
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.compose.material3)
            implementation(libs.lifecycle.runtime.compose)
            implementation(libs.sqldelight.android)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.accompanist.navigation.animation)
            implementation(libs.lottie.compose)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
            implementation(libs.ktor.client.darwin)
            // Sql Delight
            implementation(libs.sqldelight.native)
        }
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.kotlinx.coroutines.core)
            api(libs.touchlab.kermit)
            implementation(libs.koin.core)
            implementation(libs.sqldelight.coroutines)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.pi.ProjectInclusion"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
}
dependencies {
//    implementation(libs.androidx.material3.android)
//    implementation(libs.androidx.ui.tooling.preview.android)
//    implementation(libs.activity.compose)
    implementation(libs.androidx.databinding.compiler)
}

sqldelight {
    database("PiDatabase") {
        packageName = "com.pi.ProjectInclusion.database"
        version = 1
        // schemaOutputDirectory.set(file("src/commonMain/sqldelight")) // Optional
    }
}