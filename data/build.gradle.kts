plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.gms.google-services")
    kotlin("kapt")
}

android {
    namespace = "com.darestory.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(AndroidX.CORE)
    implementation(AndroidX.APPCOMPAT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.CONSTRAINT_LAYOUT)

    //힐트
    implementation(Google.HILT_ANDROID)
    implementation(Google.HILT_CORE)
    kapt(Google.HILT_COMPILER)

    implementation(platform(Google.FIREBASE_BOM))
    implementation(Google.FIREBASE_AUTH)
    implementation(Google.FIREBASE_FIRE_STORE)
    implementation(Google.FIREBASE_REALTIME_DB)
    implementation(Google.FIREBASE_FCM)
    implementation(Google.FIREBASE_ANALYTICS)

    // ROOM Database 사용
    implementation(AndroidX.ROOM_RUNTIME)
    //annotationProcessor(AndroidX.ROOM_COMPILER)
    kapt(AndroidX.ROOM_COMPILER)
    implementation(AndroidX.ROOM)


    //OKHTTP
    api(Google.OKHTTP)
    //GSON
    api(Google.GSON)
}