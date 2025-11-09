import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.1.21"
}

private val keystorePropertiesFile = rootProject.file("keystore.properties")
private val keystoreProperties = keystorePropertiesFile.inputStream().use { inputStream ->
    Properties().apply {
        load(inputStream)
    }
}

private val apiKeyKinopoiskUnOfficial = keystoreProperties.getProperty(
    "FLAVI_API_KEY_UN_OFFICIAL_KINOPOISK"
)

private val apiKeyKinopoiskDev = keystoreProperties.getProperty(
    "FLAVI_API_KEY_KINOPOISK_DEV"
)

android {
    namespace = "com.example.flavi"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.flavi"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(
            type = "String",
            name = "FLAVI_API_KEY_UN_OFFICIAL_KINOPOISK",
            value = apiKeyKinopoiskUnOfficial
        )
        buildConfigField(
            type = "String",
            name = "FLAVI_API_KEY_KINOPOISK_DEV",
            value = apiKeyKinopoiskDev
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    debugImplementation(libs.leakcanary.android)
    implementation(libs.compose)
    implementation(libs.glide)
    implementation(libs.androidx.material)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.robolectric)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.junit.v412)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.espresso.core)
    testImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.runner)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}