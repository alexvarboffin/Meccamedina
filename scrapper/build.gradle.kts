plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    packaging {
        resources.excludes.add("META-INF/*")
    }
    namespace = "com.demo.scrapper"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.demo.scrapper"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.common.java8)
    //implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.7")
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(project(":ytlib"))
    implementation(libs.androidx.core.ktx)
    implementation(project(":features:ui"))
    implementation(project(":features:permissionResolver"))

    // testImplementation("junit:junit:4.13.2")
    // androidTestImplementation("androidx.test.ext:junit:1.2.1")
    // androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // implementation("com.github.HaarigerHarald:android-youtubeExtractor:2.1.0")
    implementation(libs.gson)
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation(libs.logging.interceptor)
    implementation(project(":data"))
    implementation(project(":intentresolver"))
}