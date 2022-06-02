import com.android.build.api.dsl.Lint

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
//    kotlin("plugin.serialization") version AppConfig.version.kotlin_version
    id("kotlin-parcelize")
}

android {

    // 编译 SDK 版本
    compileSdk = AppConfig.buildConfig.compile_sdk_version
    // 编译工具版本
    buildToolsVersion = AppConfig.buildConfig.build_tools_version
    // 资源前缀
    resourcePrefix("app")

    defaultConfig {
        // 应用 id
        applicationId = AppConfig.buildConfig.application_id
        // 最低支持版本
        minSdk = AppConfig.buildConfig.min_sdk_version
        // 目标 SDK 版本
        targetSdk = AppConfig.buildConfig.target_sdk_version
        // 应用版本号
        versionCode = AppConfig.buildConfig.version_code
        // 应用版本名
        versionName = AppConfig.buildConfig.version_name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // 开启 Dex 分包
        multiDexEnabled = true
    }

    signingConfigs {
        // 签名配置
        getByName("debug") {
            keyAlias = AppConfig.signingConfig.key_alias
            keyPassword = AppConfig.signingConfig.key_password
            storeFile = file(AppConfig.signingConfig.store_file)
            storePassword = AppConfig.signingConfig.store_password
            enableV1Signing = true
            enableV2Signing = true
        }
        create("release") {
            keyAlias = AppConfig.signingConfig.key_alias
            keyPassword = AppConfig.signingConfig.key_password
            storeFile = file(AppConfig.signingConfig.store_file)
            storePassword = AppConfig.signingConfig.store_password
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isZipAlignEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            isZipAlignEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
        }
    }

    // 维度
    flavorDimensions.add("version")

    productFlavors {
        // 正式线上版本
        create("online") {
            //dimension = "version"
            // 版本名后缀
            versionNameSuffix = "_online"
            // 是否使用线上环境
            buildConfigField("boolean", "IS_ONLINE_ENV", "true")
        }

        // 测试版本
        create("offline") {
            //dimension = "version"
            // 应用包名后缀
            applicationIdSuffix = ".offline"
            // 版本名后缀
            versionNameSuffix = "_offline"
            // 是否使用线上环境
            buildConfigField("boolean", "IS_ONLINE_ENV", "false")
        }

        // 开发版本
        create("dev") {
            //dimension = "version"
            // 应用包名后缀
            applicationIdSuffix = ".dev"
            // 版本名后缀
            versionNameSuffix = "_dev"
            // 是否使用线上环境
            buildConfigField("boolean", "IS_ONLINE_ENV", "false")
        }
    }

    // 源文件路径设置
    sourceSets {
        named("main") {
            java.srcDirs("src/main/java", "src/main/kotlin")
            jni.srcDirs("libs", "jniLibs")
        }
    }

    // dex 配置
    dexOptions {
        //忽略方法数限制的检查
        jumboMode = true
        //加快重新编译的速度
        dexInProcess = true
        //是否对依赖的库进行dex预处理来是你的增量构建更快速
        preDexLibraries = true
        //为DEX编译器设置最大的堆大小
        javaMaxHeapSize = "4g"
        //设置最大的线程数量使用
        maxProcessCount = 6
        keepRuntimeAnnotatedClasses = false
    }

    buildFeatures {
        // DataBinding 开启
        dataBinding = true
        // ViewBinding 开启
        viewBinding = true
    }

    // 出现错误不终止编译
    fun Lint.() {
        // 出现错误不终止编译
        abortOnError = false
    }

    // 配置 APK 输出路径
    applicationVariants.all {
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                this.outputFileName = "devhelper_${flavorName}_${buildType.name}_v${versionName}" + ".apk"
            }
        }
    }

    // Java 版本配置
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // kotlin Jvm 版本
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += arrayOf("-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi")
    }
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.getName())
    }
}

dependencies {

    // Kotlin
    implementation(AppConfig.deps.kotlin_stdlib)
    // 协程
    implementation(AppConfig.deps.kotlin_coroutines)
    // Json 序列化
    implementation(AppConfig.deps.kotlin_serialization)
    // Dex 分包
    implementation(AppConfig.deps.androidx_multidex)
    // v4
    implementation(AppConfig.deps.androidx_legacy)
    // v7
    implementation(AppConfig.deps.androidx_appcompat)
    // design
    implementation(AppConfig.deps.androidx_material)
    // RecyclerView
    implementation(AppConfig.deps.androidx_recyclerview)
    // 约束性布局
    implementation(AppConfig.deps.androidx_constraint)
    // activity
    implementation(AppConfig.deps.androidx_activity_ktx)
    // fragment
    implementation(AppConfig.deps.androidx_fragment_ktx)
    // ktx
    implementation(AppConfig.deps.androidx_core_ktx)
    // Androidx startup
    implementation(AppConfig.deps.androidx_startup)
    // LifeCycle 拓展
    implementation(AppConfig.deps.androidx_lifecycle_ktx)
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
    // Glide
    implementation(AppConfig.deps.coil)
    //startup_runtime
    implementation(AppConfig.deps.startup_runtime)
    // MMKV 数据存储
    implementation(AppConfig.deps.tencent_mmkv)
    //Arouter
    implementation(AppConfig.deps.arouter_api)
    kapt(AppConfig.deps.arouter_compiler)

    // SmartRefreshLayout
    implementation(AppConfig.deps.smart_refresh)
    implementation(AppConfig.deps.smart_refresh_header_classics)
    implementation(AppConfig.deps.smart_refresh_footer_classics)

    // 状态栏工具
    implementation(AppConfig.deps.immersion_bar)
    implementation(AppConfig.deps.immersion_bar_ktx)

    // 换肤
    implementation(AppConfig.deps.skin_support)
    implementation(AppConfig.deps.skin_support_appcompat)
    implementation(AppConfig.deps.skin_support_material)
    implementation(AppConfig.deps.skin_support_cardview)
    implementation(AppConfig.deps.skin_support_constraint)

    // Tablayout
    implementation(AppConfig.deps.tablayout)
    //BaseRecyclerViewAdapterHelper
    implementation(AppConfig.deps.base_rv_helper)

    // 依赖 base 库
//    implementation(project(":lib_ui"))
//    implementation(project(":lib_databinding_adapter"))
//    implementation(project(":lib_okhttp_interceptor"))
//    implementation(project(":lib_recyclerview"))
//    implementation(project(":lib_views_custom"))

    implementation(project(":lib-base"))
    implementation(project(":lib-common"))

    // 测试
    testImplementation(AppConfig.deps.test_junit)
    androidTestImplementation(AppConfig.deps.androidx_test_runner)
    androidTestImplementation(AppConfig.deps.androidx_test_espresso_core)
    androidTestImplementation(AppConfig.deps.androidx_test_ext_junit)
}