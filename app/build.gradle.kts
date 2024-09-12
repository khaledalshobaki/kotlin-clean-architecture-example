plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.sample.kotlin.cleanarchitecture"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sample.kotlin.cleanarchitecture"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val retrofitVersion = "2.11.0"
    val roomVersion = "2.6.1"
    val coreKtxVersion = "1.13.1"
    val lifecycleRuntimeKtxVersion = "2.8.3"

    val composeBomVersion = "2023.08.00"
    val activityComposeVersion = "1.9.0"
    val navigationComposeVersion = "2.7.7"

    val hiltAndroidVersion = "2.51.1"
    val hiltNavigationComposeVersion = "1.2.0"
    val hiltCompilerVersion = "2.44.2"

    val junitVersion = "4.13.2"
    val extJUnitVersion = "1.2.1"
    val espressoCoreVersion = "3.6.1"

    val hiltWorkVersion = "1.2.0"
    val workManagerVersion = "2.9.0"

    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeKtxVersion")

    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation("androidx.navigation:navigation-compose:$navigationComposeVersion")

    implementation("com.google.dagger:hilt-android:$hiltAndroidVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")
    ksp("com.google.dagger:hilt-android-compiler:$hiltCompilerVersion")

    implementation("androidx.work:work-runtime-ktx:${workManagerVersion}")
    implementation("androidx.hilt:hilt-work:${hiltWorkVersion}")
    ksp("androidx.hilt:hilt-compiler:${hiltWorkVersion}")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-simplexml:$retrofitVersion")

    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    testImplementation("junit:junit:$junitVersion")

    androidTestImplementation("androidx.test.ext:junit:$extJUnitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCoreVersion")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}