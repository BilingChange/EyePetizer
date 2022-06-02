plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = AppConfig.buildConfig.compile_sdk_version
    // 资源前缀
    resourcePrefix("common")

    defaultConfig {
        minSdk = AppConfig.buildConfig.min_sdk_version
        targetSdk = AppConfig.buildConfig.target_sdk_version

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
        }
    }

    buildFeatures {
        // DataBinding 开启
        dataBinding = true
        // ViewBinding 开启
        viewBinding = true
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

    implementation(AppConfig.deps.androidx_core_ktx)
    implementation(AppConfig.deps.androidx_appcompat)
    implementation(AppConfig.deps.androidx_material)
    implementation(AppConfig.deps.tencent_mmkv)
    implementation(AppConfig.deps.androidx_lifecycle_common_java8)

    //Test
    testImplementation(AppConfig.deps.test_junit)
    androidTestImplementation(AppConfig.deps.androidx_test_ext_junit)
    androidTestImplementation(AppConfig.deps.androidx_test_espresso_core)
}