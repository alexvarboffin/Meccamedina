plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {


    namespace = "com.example.ytlib"

    compileSdk = 35
    buildToolsVersion = rootProject.extra["buildToolsVersion0"].toString()

    defaultConfig {
        minSdk = rootProject.extra["minSdkVersion0"].toString().toInt()
        targetSdk = rootProject.extra["targetSdkVersion0"].toString().toInt()
//        versionCode = 1
//        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            consumerProguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout.v214)

    // testImplementation("junit:junit:4.12")
    // androidTestImplementation("com.android.support.test:runner:1.0.1")
    // androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.1")

    implementation(libs.androidx.cardview)



    // Dagger
    implementation(libs.dagger)
    

    // ButterKnife
    implementation(libs.butterknife)
    annotationProcessor(libs.butterknife.compiler)

    implementation(libs.androidx.preference)

    // YouTube
    implementation("com.github.tommus:youtube-android-player-api:1.2.3") {
        exclude(group = "org.apache.httpcomponents")
        exclude(group = "org.apache.httpcomponents", module = "httpclient")
    }
    implementation("com.google.api-client:google-api-client-android:2.0.0") {
        exclude(group = "org.apache.httpcomponents")
    }

    // Firebase Ads
    implementation("com.google.firebase:firebase-ads:23.0.0") {
        exclude(group = "com.android.support")
    }

    // Easy Permissions
    implementation(libs.easypermissions)

    // Google API Services
    implementation("com.google.apis:google-api-services-youtube:v3-rev99-1.17.0-rc") {
        exclude(group = "org.apache.httpcomponents")
    }

    // Material Drawer
    implementation("com.mikepenz:materialdrawer:6.1.2@aar") {
        isTransitive = true
    }

    // Ultimate RecyclerView
    implementation(libs.ultimaterecyclerview)

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    // Local modules
    implementation(project(":features:ui"))

    // Local JAR files
    implementation(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))
    // implementation(files("lib/YouTubeAndroidPlayerApi.jar"))
}