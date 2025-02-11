plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

android {

    namespace = "com.walhalla.ytlib"

    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(libs.androidx.appcompat)
    api(libs.androidx.material)
    //============
    //implementation ("com.github.Zuhairdi:UltimateRecyclerView:313fae8080")
    //implementation("com.marshalchen:ultimaterecyclerview.library:0.9.0")
    //implementation 'com.marshalchen.ultimaterecyclerview:library:0.9.0'
    implementation(libs.gson)
    implementation(libs.firebase.ads)
    implementation(libs.firebase.database)


    api(libs.okhttp)
    api(libs.ultimaterecyclerview)
    api(libs.recyclerview.animators)

    implementation(libs.glide)
    implementation(project(":features:ui"))
    implementation(project(":pdfViewer"))
    implementation(libs.androidx.core.ktx)
    ksp("com.github.bumptech.glide:ksp:4.16.0")

    api(libs.easypermissions)
    api(libs.google.api.client.android)
    api("com.google.apis:google-api-services-youtube:v3-rev20210915-1.32.1")
    {
        //exclude group: "org.apache.httpcomponents"
    }
    api(libs.materialdrawer)

    // Moxy
    implementation(libs.moxy.ktx)
    //annotationProcessor(libs.moxy.compiler)
    implementation(libs.moxy.androidx)

    implementation(libs.materialloadingprogressbar)

    // New Player
    implementation(libs.androidyoutubeplayer.core)
    implementation(libs.chromecast.sender)
    //============
}