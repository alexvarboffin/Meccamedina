plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    //id("com.android.library") apply false //alias(libs.plugins.android.library) apply false
    //alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.kotlin.android) apply false
    //kotlin("android") version "2.1.0" apply false
    //
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
    kotlin("kapt") version "2.1.10" apply false
}

extra.apply {
    set("minSdkVersion0", 21)
    set("kotlin_version", "1.9.0")
    // set("glideVersion", "4.16.0") // или set("glideVersion", "4.15.1")
    set("firebaseDatabaseVersion", "20.3.0")
    set("okHttpVersion", "3.12.12")
}
//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//        mavenLocal()
//        gradlePluginPortal()
//        maven { url = uri("https://www.jitpack.io") }
//    }
//    dependencies {
//        classpath("com.android.tools.build:gradle:8.2.2")
//        classpath("com.google.gms:google-services:4.4.2")
//        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
//    }
//}
//
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//        mavenLocal()
//        maven { url = uri("https://jitpack.io") }
//        @Suppress("JcenterRepositoryObsolete")
//        jcenter()
//    }
//}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}