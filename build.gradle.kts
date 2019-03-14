buildscript {

    repositories {
        google()
        jcenter()
        maven { url = uri ("https://maven.fabric.io/public") }
    }

    dependencies {
        classpath ("com.android.tools.build:gradle:3.3.2")
        classpath ("com.google.gms:google-services:4.2.0")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Deps.kotlin_version}")
        classpath ("android.arch.navigation:navigation-safe-args-gradle-plugin:${Deps.navigation_version}")
        classpath ("io.fabric.tools:gradle:1.26.1")
        classpath ("com.dicedmelon.gradle:jacoco-android:0.1.2")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri ("https://maven.fabric.io/public") }
    }
}
