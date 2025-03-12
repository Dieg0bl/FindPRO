plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
}

android {
    namespace = "dbl.findpro"
    compileSdk = 35

    defaultConfig {
        applicationId = "dbl.findpro"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildFeatures {
        compose = true
        buildConfig = true // Activar BuildConfig
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

    buildTypes {
        release {
            buildConfigField("boolean", "DEBUG", "false")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("boolean", "DEBUG", "true")
            isMinifyEnabled = false
        }
    }

    lint {
        abortOnError = true
        warningsAsErrors = true
    }
}

dependencies {
    // 🚀 Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-config-ktx:22.1.0")
    implementation("com.google.firebase:firebase-analytics-ktx:22.2.0")
    implementation("com.google.firebase:firebase-crashlytics-ktx:19.4.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.2.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.2")

    // 🚀 Hilt (Inyección de Dependencias)
    implementation("com.google.dagger:hilt-android:2.55")
    implementation(project(":mapservice"))
    implementation(project(":core"))
    implementation(project(":authentication"))
    implementation(project(":userprofiles"))
    ksp("com.google.dagger:hilt-compiler:2.55")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // 🚀 UI y Navegación
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.runtime:runtime:1.7.8")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.navigation:navigation-compose:2.8.7")

    // 🚀 Mapbox
    implementation("com.mapbox.base:common:0.11.0")

    // 🚀 Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.1")

    // 🚀 Timber (Logging)
    implementation("com.jakewharton.timber:timber:5.0.1")

    // 🚀 Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.8")
}
