import java.text.SimpleDateFormat
import java.util.Date

// buildscript {
//     repositories {
//         maven { url = uri("https://plugins.gradle.org/m2/") }
//     }
//     dependencies {
//         // OneSignal-Gradle-Plugin
//         classpath("gradle.plugin.com.onesignal:onesignal-gradle-plugin:[0.14.0, 0.99.99]")
//     }
// }

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.firebase.crashlytics")
    //kotlin("android")
    id("com.google.gms.google-services")
    // id("com.onesignal.androidsdk.onesignal-gradle-plugin")
    kotlin("kapt")
    id("com.google.devtools.ksp")
}



android {
    namespace = "ai.meccamedinatv.mekkalive.online"

    // dexOptions {
    //     javaMaxHeapSize = "8G"
    // }

    packaging {
        resources.excludes.add("META-INF/*")
    }

    compileSdk = 35
    buildToolsVersion = "35.0.0"

    // compileSdkVersion = 30
    // buildToolsVersion = "30.0.3"

    val versionPropsFile = file("version.properties")
    if (versionPropsFile.canRead()) {
        // val versionProps = Properties().apply {
        //     load(FileInputStream(versionPropsFile))
        // }
        // val code = versionProps["VERSION_CODE"].toString().toInt() + 1
        // versionProps["VERSION_CODE"] = code.toString()
        // versionProps.store(versionPropsFile.writer(), null)

        val code = versionCodeDate()

        defaultConfig {
            multiDexEnabled = true
            resConfigs("en", "es", "fr", "de", "it", "pt", "el", "ru", "ja", "zh-rCN", "zh-rTW", "ko", "ar", "uk", "vi", "uz", "az")

            vectorDrawables {
                useSupportLibrary = true
            }

            applicationId = "ai.meccamedinatv.mekkalive.online"

            minSdk = rootProject.extra["minSdkVersion0"].toString().toInt()
            targetSdk = rootProject.extra["targetSdkVersion0"].toString().toInt()

            versionCode = code
            versionName = "3.2.$code"
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            // signingConfig = signingConfigs.config

            // javaCompileOptions {
            //     annotationProcessorOptions {
            //         includeCompileClasspath = true
            //     }
            // }

            setProperty("archivesBaseName", "meccamedina")
//            manifestPlaceholders = mapOf(
//                "onesignal_app_id" to "1b064a29-0b38-4562-aa73-2bc13cd4bf63",
//                "onesignal_google_project_number" to "REMOTE" // 734593759786
//            )
        }
    } else {
        throw GradleException("Could not read version.properties!")
    }

    signingConfigs {
        getByName("debug") {
            // storeFile = file("D:\\android\\keystore\\debug.keystore")
            // storePassword = "android"
            // keyAlias = "AndroidDebugKey"
            // keyPassword = "android"

            // keyAlias = "meccamedina"
            // keyPassword = "@!sfuQ123zpc"
            // storeFile = file("D:\\walhalla\\sign\\keystore.jks")
            // storePassword = "@!sfuQ123zpc"
        }

        create("release") {
            // keyAlias = RELEASE_KEY_ALIAS
            // keyPassword = RELEASE_KEY_PASSWORD
            // storeFile = file(RELEASE_STORE_FILE)
            // storePassword = RELEASE_STORE_PASSWORD

            // keyAlias = "meccamedina"
            // keyPassword = "@!sfuQ123zpc"
            // storeFile = file("D:\\walhalla\\sign\\keystore.jks")
            // storePassword = "@!sfuQ123zpc"

            keyAlias = "release"
            keyPassword = "release"
            storeFile = file("keystore/keystore.jks")
            storePassword = "release"
        }

        // create("meccamedina") {
        //     keyAlias = "meccamedina"
        //     keyPassword = "@!sfuQ123zpc"
        //     storeFile = file("D:\\walhalla\\sign\\keystore.jks")
        //     storePassword = "@!sfuQ123zpc"
        // }
    }

    buildTypes {
        getByName("debug") {
            multiDexEnabled = true
            isDebuggable = true
            // isMinifyEnabled = true
            // proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules-debug.pro")
            signingConfig = signingConfigs.getByName("release")
            versionNameSuffix = "-DEMO"
        }

        getByName("release") {
            multiDexEnabled = true
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
            isJniDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            versionNameSuffix = ".release"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // flavorDimensions += "build"
    // productFlavors {
    //     create("google") {
    //         dimension = "build"
    //         signingConfig = signingConfigs.getByName("meccamedina")
    //     }
    // }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

//    lint {
//        isAbortOnError = false
//    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

// ==========
tasks.register<Copy>("copyAabToBuildFolder") {
    println("mmmmmmmmmmmmmmmmm ${buildDir}/outputs/bundle/release")

    val outputDirectory = file("C:/build")
    if (!outputDirectory.exists()) {
        outputDirectory.mkdirs()
    }

    from("${buildDir}/outputs/bundle/release") {
        include("*.aab")
    }
    into(outputDirectory)
}

apply(from = "C:\\scripts/copyReports.gradle")

// ==========

dependencies {
    // implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // implementation(files("libs/player_1_2_2.jar"))

    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.cardview)

    // androidX
    // implementation("tech.schoolhelper:moxy-x-app-compat:1.7.0")
    // implementation("tech.schoolhelper:moxy-x-androidx:1.7.0")
    // implementation("tech.schoolhelper:moxy-x:1.7.0")
    // annotationProcessor("tech.schoolhelper:moxy-x-compiler:1.7.0")

    implementation(libs.androidx.preference)

    // implementation("com.github.tommus:youtube-android-player-api:1.2.3")
    // implementation("com.github.tommus:youtube-android-player-api:1.2.3") {
    //     exclude(group = "org.apache.httpcomponents")
    //     exclude(group = "org.apache.httpcomponents", module = "httpclient")
    // }

    // implementation("com.google.android.gms:play-services:11.4.2")

    // implementation("com.mikepenz:materialdrawer:6.1.2@aar") {
    //     isTransitive = true
    // }

    implementation(libs.materialloadingprogressbar)

    // Firebase Crashlytics
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)

    implementation(libs.glide)
    ksp("com.github.bumptech.glide:ksp:4.16.0")
    implementation(libs.firebase.database)
    implementation(libs.firebase.ads)

    implementation(project(":features:ui"))

    implementation(project(":pdfViewer"))

    implementation(project(":features:wads"))
    implementation(project(":ytlib"))

    implementation(libs.androidx.lifecycle.process)
    implementation("androidx.lifecycle:lifecycle-runtime:2.8.7")
    implementation(libs.androidx.lifecycle.common.java8)
    //implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.7")
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.pulsator4droid)
    implementation(libs.google.api.client.jackson2)

    implementation(libs.listenablefuture)
    implementation(libs.androidx.multidex)

    implementation(libs.guava)
    implementation(libs.kotlin.stdlib.jdk8)

    // New Player
    implementation(libs.androidyoutubeplayer.core)
    implementation(libs.chromecast.sender)

    implementation(libs.onesignal)

    // Moxy
    //implementation(libs.moxy)
    //annotationProcessor(libs.moxy.compiler)
    implementation(libs.moxy.androidx)
    implementation(libs.moxy.ktx)
    kapt(libs.dagger.compiler)
    kapt(libs.moxy.compiler)
}

fun versionCodeDate(): Int {
    return SimpleDateFormat("yyMMdd").format(Date()).toInt()
}