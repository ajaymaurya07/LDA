plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "2.0.0-1.0.24"
    id("kotlin-parcelize")
}
// ecout project
android {
    namespace = "com.example.lda"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.vdsai.house_tax_final"
        minSdk = 24
        targetSdk = 35
        versionCode = 3
        versionName = "2.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.camera.core)
    implementation (libs.androidx.camera.camera2)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.view)
    implementation (libs.androidx.camera.extensions)


    // retrofit
    implementation(libs.retrofit)
    // GSON
    implementation(libs.converter.gson)

    implementation (libs.logging.interceptor)


    implementation (libs.androidx.viewpager2)
    implementation (libs.glide)
    implementation (libs.circleindicator)


    implementation ("androidx.room:room-runtime:2.8.2")
    ksp ("androidx.room:room-compiler:2.8.2")
    implementation ("androidx.room:room-ktx:2.6.1")

    // payu for payment integration
    implementation ("in.payu:payu-checkout-pro:3.0.3")


}