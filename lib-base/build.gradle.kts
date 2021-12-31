plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {

    // 编译 SDK 版本
    compileSdkVersion(AppConfig.buildConfig.compile_sdk_version)
    // 编译工具版本
    buildToolsVersion(AppConfig.buildConfig.build_tools_version)
    // 资源前缀
    resourcePrefix("base")

    defaultConfig {
        // 最低支持版本
        minSdkVersion(AppConfig.buildConfig.min_sdk_version)
        // 目标 SDK 版本
        targetSdkVersion(AppConfig.buildConfig.target_sdk_version)
        // 应用版本号
        versionCode = AppConfig.buildConfig.version_code
        // 应用版本名
        versionName = AppConfig.buildConfig.version_name
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
        }
    }

    // 源文件路径设置
    sourceSets {
        named("main") {
            java.srcDirs("src/main/java", "src/main/kotlin")
            jni.srcDirs("libs", "jniLibs")
        }
    }

    buildFeatures {
        // DataBinding 开启
        dataBinding = true
        // ViewBinding 开启
        viewBinding = true
    }

    lintOptions {
        // 出现错误不终止编译
        isAbortOnError = false
    }

    // Java 版本配置
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // kotlin Jvm 版本
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // 测试
    testImplementation(AppConfig.deps.test_junit)
    androidTestImplementation(AppConfig.deps.androidx_test_runner)
    androidTestImplementation(AppConfig.deps.androidx_test_espresso_core)
    androidTestImplementation(AppConfig.deps.androidx_test_ext_junit)

    // core-ktx
    implementation(AppConfig.deps.androidx_core_ktx)
    // appcompat
    implementation(AppConfig.deps.androidx_appcompat)
    // material
    implementation(AppConfig.deps.androidx_material)
    // RecyclerView
    implementation(AppConfig.deps.androidx_recyclerview)
    // 约束性布局
    implementation(AppConfig.deps.androidx_constraint)
    // activity
    implementation(AppConfig.deps.androidx_activity_ktx)
    // fragment
    implementation(AppConfig.deps.androidx_fragment_ktx)
    // startup-runtime
    implementation(AppConfig.deps.startup_runtime)

    // LifeCycle 拓展
    implementation(AppConfig.deps.androidx_lifecycle_ktx)
    implementation(AppConfig.deps.androidx_lifecycle_common_java8)
    implementation(AppConfig.deps.androidx_lifecycle_extensions)
    // ViewModel 拓展
    implementation(AppConfig.deps.androidx_lifecycle_viewmodel_ktx)
    // LiveData 拓展
    implementation(AppConfig.deps.androidx_lifecycle_livedata_ktx)
    // viewpager2
    implementation(AppConfig.deps.androidx_viewpager2)
    // Logger
    implementation(AppConfig.deps.logger)
    // Koin
    implementation(AppConfig.deps.koin_scope)
    implementation(AppConfig.deps.koin_viewmodel)
    implementation(AppConfig.deps.koin_ext)
    // LiveEventBus
    implementation(AppConfig.deps.live_event_bus)
    // OkHttp
    implementation(AppConfig.deps.okhttp)
    // Retrofit
    implementation(AppConfig.deps.retrofit)
    implementation(AppConfig.deps.retrofit_converter_kt)
    // coil
    implementation(AppConfig.deps.coil)
    // MMKV 数据存储
    implementation(AppConfig.deps.tencent_mmkv)
    implementation(AppConfig.deps.arouter_api)
    kapt(AppConfig.deps.arouter_compiler)

    // SmartRefreshLayout
    implementation(AppConfig.deps.smart_refresh)
    implementation(AppConfig.deps.smart_refresh_header_classics)
    implementation(AppConfig.deps.smart_refresh_footer_classics)

    // 状态栏工具
    implementation(AppConfig.deps.immersion_bar)
    implementation(AppConfig.deps.immersion_bar_ktx)


    // Tablayout
    implementation(AppConfig.deps.tablayout)
    //BaseRecyclerViewAdapterHelper
    implementation(AppConfig.deps.base_rv_helper)
    implementation(AppConfig.deps.multitype)

}