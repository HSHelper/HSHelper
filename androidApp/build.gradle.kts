plugins {
    id("com.android.application")
    kotlin("android")
}

group = "ru.HSHelper"
version = "0.0.0"

dependencies {
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "ru.HSHelper.androidApp"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "0.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}