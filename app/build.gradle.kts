plugins {
    id("com.android.application")
    id("kotlin-android")
    id("io.fabric")
    id("androidx.navigation.safeargs")
    id("jacoco-android")
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = file("$projectDir/debug.keystore")
            storePassword = "android"
        }
    }

    compileSdkVersion(Deps.compileSdkVersion)
    defaultConfig {
        applicationId = "com.stazis.subwaystationsmvvm"
        minSdkVersion(Deps.minSdkVersion)
        targetSdkVersion(Deps.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules-debug.pro")
            isTestCoverageEnabled = true
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    lintOptions {
        setLintConfig(file("$projectDir/lint.xml"))
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    // Anko
    implementation("org.jetbrains.anko:anko-sdk25:${Deps.ankoVersion}")
    implementation("org.jetbrains.anko:anko-appcompat-v7:${Deps.ankoVersion}")

    // Androidx
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.core:core-ktx:1.0.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Deps.coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Deps.coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Deps.coroutinesVersion}")

    // Crashlytics
    implementation("com.crashlytics.sdk.android:crashlytics:2.9.9")

    // Firebase
    implementation("com.google.firebase:firebase-core:16.0.7")
    implementation("com.google.firebase:firebase-firestore:18.1.0")

    // Koin
    implementation("org.koin:koin-android:${Deps.koinVersion}")
    implementation("org.koin:koin-androidx-viewmodel:${Deps.koinVersion}")
    implementation("org.koin:koin-core-ext:${Deps.koinVersion}")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Deps.kotlinVersion}")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.1.0-alpha02")

    // Location
    implementation("com.google.android.gms:play-services-location:16.0.0")

    // Maps
    implementation("com.google.android.gms:play-services-maps:16.1.0")
    implementation("com.google.maps.android:android-maps-utils:0.5")

    // Navigation
    implementation("android.arch.navigation:navigation-fragment-ktx:${Deps.navigationVersion}")
    implementation("android.arch.navigation:navigation-ui-ktx:${Deps.navigationVersion}")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${Deps.retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${Deps.retrofitVersion}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    // Testing
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.24.0")
    testImplementation("org.koin:koin-test:${Deps.koinVersion}")
}