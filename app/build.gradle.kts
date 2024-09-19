plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.sample.rickandmorty"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sample.rickandmorty"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_URL", "\"https://rickandmortyapi.com/api/\"")
        buildConfigField ("int", "PAGE_SIZE", "20")

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Version variables for easier updates and consistency across dependencies
    val kotlinxSerializationVersion = "1.6.3"
    val glideVersion = "1.0.0-beta01"
    val retrofitVersion = "2.11.0"
    val roomVersion = "2.6.1"
    val coreKtxVersion = "1.13.1"
    val lifecycleRuntimeKtxVersion = "2.8.3"
    val uiControllerVersion = "0.28.0"

    val composeBomVersion = "2023.08.00"
    val activityComposeVersion = "1.9.0"
    val navigationComposeVersion = "2.8.0"

    val hiltAndroidVersion = "2.51.1"
    val hiltNavigationComposeVersion = "1.2.0"
    val hiltCompilerVersion = "2.51.1"

    val junitVersion = "4.13.2"
    val extJUnitVersion = "1.2.1"
    val espressoCoreVersion = "3.6.1"

    val hiltWorkVersion = "1.2.0"
    val workManagerVersion = "2.9.0"

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    // Core AndroidX library with Kotlin extensions for easier Android development
    implementation("androidx.core:core-ktx:$coreKtxVersion")

    // Lifecycle-aware components and extensions for managing Android lifecycles
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeKtxVersion")

    // Compose BOM (Bill of Materials) to manage versions for all Compose dependencies
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))

    // Core Compose UI components
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    // Material 3 components for Compose
    implementation("androidx.compose.material3:material3")

    // Compose integration with activity components
    implementation("androidx.activity:activity-compose:$activityComposeVersion")

    // Navigation component integration for Compose
    implementation("androidx.navigation:navigation-compose:$navigationComposeVersion")

    // Hilt dependency injection framework for Android
    implementation("com.google.dagger:hilt-android:$hiltAndroidVersion")
    // Hilt integration with Jetpack Compose
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")
    // Hilt compiler for annotation processing with KSP (Kotlin Symbol Processing)
    ksp("com.google.dagger:hilt-android-compiler:$hiltCompilerVersion")

    // WorkManager for scheduling background work in Android
    implementation("androidx.work:work-runtime-ktx:${workManagerVersion}")
    // Hilt integration with WorkManager
    implementation("androidx.hilt:hilt-work:${hiltWorkVersion}")
    // Hilt compiler for WorkManager-related annotations
    ksp("androidx.hilt:hilt-compiler:${hiltWorkVersion}")

    // Glide image loading library integration with Jetpack Compose
    implementation("com.github.bumptech.glide:compose:$glideVersion")

    // Kotlin serialization library for JSON parsing and formatting
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

    // Accompanist library for controlling system UI properties (e.g., status bar, navigation bar)
    implementation("com.google.accompanist:accompanist-systemuicontroller:$uiControllerVersion")

    // Retrofit for making network requests and integrating with APIs
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    // Gson converter for converting JSON responses into Kotlin objects
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Room database for local storage with Kotlin extensions
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    // Room compiler for annotation processing with KSP
    ksp("androidx.room:room-compiler:$roomVersion")

    // Additional tools for Compose UI debugging and testing
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // JUnit 4 for unit testing
    testImplementation("junit:junit:$junitVersion")

    // AndroidX testing extensions for JUnit and Espresso for UI testing
    androidTestImplementation("androidx.test.ext:junit:$extJUnitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCoreVersion")
    // Compose testing BOM for managing versions of all testing dependencies
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    // JUnit 4 testing integration for Compose UI tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
