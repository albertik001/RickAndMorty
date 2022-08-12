plugins {
    // Application
    id(libs.plugins.agp.library.get().pluginId)

    // Kotlin
    id("kotlin-android")

    // Kapt
    id("kotlin-kapt")

    // Hilt
    id(libs.plugins.hilt.android.get().pluginId)
}

android {
    compileSdk = config.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = config.versions.minSdk.get().toInt()
        targetSdk = config.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName(config.versions.releaseBuildType.get()) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName(config.versions.debugBuildType.get()) {
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = options.versions.kotlinJvmTargetOptions.get()
    }
}

dependencies {

    api(project(":domain"))

    // Retrofit
    implementation(libs.bundles.retrofit)

    // OkHttp
    implementation(libs.bundles.okHttp)

    // Room
    api(libs.bundles.room)
    kapt(libs.room.compiler)

    //Paging 3
    api(libs.paging.paging)
    api(libs.bundles.paging)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

}