import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

//    import plugins
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sqldelight)


    // Add kapt plugin for Android
    id("kotlin-kapt")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            //android dependencies for ktor client and sqlDelight driver
            implementation(libs.ktor.client.android)
            implementation(libs.android.driver)

            //android dependencies
            implementation(libs.hilt.android)
            implementation(libs.androidx.hilt.navigation.compose)
            //here we use configurations["kapt"] instead of kapt()
            configurations["kapt"].dependencies.add(DefaultExternalModuleDependency("com.google.dagger", "hilt-android-compiler", "2.42"))
            configurations["kapt"].dependencies.add(DefaultExternalModuleDependency("androidx.hilt", "hilt-compiler", "1.0.0"))

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            //shared dependencies
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.sqlDelight.runtime)
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)

        }
        iosMain.dependencies {
            //native dependencies for ktor client and sqlDelight driver
            implementation(libs.ktor.client.darwin)
            implementation(libs.native.driver)
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
}

sqldelight {
    databases {
    create("MyDatabase") {
        packageName = "com.csahmed2020.demo.sqldelight"
    }
}
}

android {
    namespace = "com.csahmed2020.demo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.csahmed2020.demo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        // Enable core library desugaring for Java 8+ APIs
        isCoreLibraryDesugaringEnabled = true
        
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)

        //Enable core library desugaring It is used to enable certain Java 8+ features on lower Android API levels.
        coreLibraryDesugaring(libs.desugar.jdk.libs)
    }

}
dependencies {
    //implementation(project(":composeApp"))
}
