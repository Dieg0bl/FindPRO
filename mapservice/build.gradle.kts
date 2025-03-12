plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
}

android {
    namespace = "dbl.findpro.features.mapservices"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core"))

    // 🚀 Jetpack Compose y UI
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.runtime:runtime:1.7.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.8")
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.8")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.navigation:navigation-compose:2.8.7")
    implementation(platform("androidx.compose:compose-bom:2025.02.00"))

    // 🚀 Core Android y Ciclo de Vida
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("io.coil-kt:coil-compose:2.5.0")

    // 🚀 Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.2")

    // 🚀 Coroutines para Firebase y Retrofit
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.1")

    // 🚀 Google Play Services (Ubicación)
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // 🚀 Mapbox
    implementation("com.mapbox.maps:android:11.9.2") {
        exclude(group = "com.google.android.gms", module = "play-services")
    }
    implementation("com.mapbox.extension:maps-compose:11.9.0")
    implementation("com.mapbox.mapboxsdk:mapbox-sdk-turf:6.11.0")
    implementation("com.mapbox.base:common:0.11.0") // 🔹 Acceso a funcionalidades comunes

    // 🚀 Retrofit + OkHttp (para llamadas HTTP)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0") // 🔹 Cliente HTTP
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // 🔹 Interceptor para logs

    // 🚀 Hilt (Inyección de Dependencias)
    implementation("com.google.dagger:hilt-android:2.55")
    ksp("com.google.dagger:hilt-compiler:2.55")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // 🚀 Logging (Timber)
    implementation("com.jakewharton.timber:timber:5.0.1")

    // 🚀 Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}


