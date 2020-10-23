package com.getstream.sdk.chat

private const val ACTIVITY_KTX_VERSION = "1.2.0-beta01"
private const val ANDROID_GRADLE_PLUGIN_VERSION = "4.0.1"
private const val ANDROID_JUNIT5_GRADLE_PLUGIN_VERSION = "1.6.2.0"
private const val ANDROID_MAVEN_GRADLE_PLUGIN_VERSION = "2.1"
private const val ANDROID_LEGACY_SUPPORT = "1.0.0"
private const val ANDROIDX_APPCOMPAT_VERSION = "1.2.0"
private const val ANDROIDX_CORE_TEST_VERSION = "2.1.0"
private const val ANDROIDX_KTX_VERSION = "1.3.1"
private const val ANDROIDX_LIFECYCLE_EXTENSIONS_VERSION = "2.2.0"
private const val ANDROIDX_LIFECYCLE_LIVEDATA_KTX_VERSION = "2.3.0-beta01"
private const val ANDROIDX_LIFECYCLE_VIEWMODEL_KTX_VERSION = "2.2.0"
private const val ANDROIDX_MEDIA_VERSION = "1.1.0"
private const val ANDROIDX_SECURITY_CRYPTO_VERSION = "1.1.0-alpha02"
private const val ANDROIDX_TEST_JUNIT_VERSION = "1.1.2"
private const val ANDROIDX_TEST_VERSION = "1.3.0"
private const val ASSERTJ_VERSION = "3.15.0"
private const val AWAITILITY_VERSION = "4.0.2"
private const val COIL_VERSION = "0.11.0"
private const val CONSTRAINT_LAYOUT_VERSION = "2.0.0"
private const val COROUTINES_VERSION = "1.3.8"
private const val DEXTER_VERSION = "6.2.1"
private const val DRAWABLETOOLBOX_VERSION = "1.0.7"
private const val DOTENV_VERSION = "5.1.4"
private const val ESPRESSO_VERSION = "3.3.0"
private const val EXOPLAYER_VERSION = "2.12.0"
private const val FIREBASE_ANALYTICS_VERSION = "17.4.4"
private const val FIREBASE_CORE_VERSION = "17.3.0"
private const val FIREBASE_CRASHLYTICS_VERSION = "17.1.1"
private const val FIREBASE_MESSAGING_VERSION = "20.2.4"
private const val FIREBASE_PLUGIN_VERSION = "2.2.0"
private const val FRAGMENT_KTX_VERSION = "1.3.0-beta01"
private const val FRESCO_VERSION = "2.3.0"
private const val GITVERSIONER_VERSION = "0.5.0"
private const val GLIDE_VERSION = "4.11.0"
private const val GLIDE_REDIRECT_VERSION = "2.0.1"
private const val GOOGLE_SERVICES_VERSION = "4.3.3"
private const val GRADLE_VERSIONS_PLUGIN = "0.29.0"
private const val GSON_VERSION = "2.8.6"
private const val JACOCO_VERSION = "0.2"
private const val JSON_VERSION = "20190722"
private const val JUNIT4_VERSION = "4.13"
private const val JUNIT5_VERSION = "5.6.2"
private const val KEYBOARD_VISIBILITY_EVENT_VERSION = "2.3.0"
private const val KFIXTURE_VERSION = "0.2.0"
private const val KLUENT_VERSION = "1.61"
private const val KOIN_VERSION = "2.1.6"
private const val KOTLIN_VERSION = "1.4.10"
private const val KTLINT_VERSION = "9.4.1"
private const val LEAK_CANARY_VERSION = "2.4"
private const val MATERIAL_COMPONENTS_VERSION = "1.1.0"
private const val MARWON_VERSION = "4.4.0"
private const val MOKITO_KOTLIN_VERSION = "2.2.0"
private const val MOKITO_VERSION = "3.4.6"
private const val NAVIGATION_VERSION = "2.3.0"
private const val OK2CURL_VERSION = "0.6.0"
private const val OKHTTP_VERSION = "4.8.0"
private const val PHOTODRAWEEVIEW_VERSION = "1.1.0"
private const val PREFERENCES_VERSION = "1.1.1"
private const val RECYCLERVIEW_VERSION = "1.2.0-alpha05"
private const val RETROFIT_VERSION = "2.9.0"
private const val ROBOLECTRIC_VERSION = "4.3.1"
private const val ROOM_VERSION = "2.2.5"
private const val STETHO_VERSION = "1.5.1"
private const val TIMBER_VERSION = "4.7.1"
private const val TRUTH_VERSION = "1.0.1"
private const val WORK_VERSION = "2.4.0"


object Dependencies {
    const val activityKtx = "androidx.activity:activity-ktx:$ACTIVITY_KTX_VERSION"
    const val androidGradlePlugin = "com.android.tools.build:gradle:$ANDROID_GRADLE_PLUGIN_VERSION"
    const val androidJunit5GradlePlugin = "de.mannodermaus.gradle.plugins:android-junit5:$ANDROID_JUNIT5_GRADLE_PLUGIN_VERSION"
    const val androidMavenGradlePlugin = "com.github.dcendents:android-maven-gradle-plugin:$ANDROID_MAVEN_GRADLE_PLUGIN_VERSION"
    const val androidLegacySupport = "androidx.legacy:legacy-support-v4:$ANDROID_LEGACY_SUPPORT"
    const val androidxAppCompat = "androidx.appcompat:appcompat:$ANDROIDX_APPCOMPAT_VERSION"
    const val androidxArchCoreTest = "androidx.arch.core:core-testing:$ANDROIDX_CORE_TEST_VERSION"
    const val androidxCoreKtx = "androidx.core:core-ktx:$ANDROIDX_KTX_VERSION"
    const val androidxLifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:$ANDROIDX_LIFECYCLE_EXTENSIONS_VERSION"
    const val androidxLifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$ANDROIDX_LIFECYCLE_LIVEDATA_KTX_VERSION"
    const val androidxLifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$ANDROIDX_LIFECYCLE_VIEWMODEL_KTX_VERSION"
    const val androidxMedia = "androidx.media:media:$ANDROIDX_MEDIA_VERSION"
    const val androidxSecurityCrypto = "androidx.security:security-crypto:$ANDROIDX_SECURITY_CRYPTO_VERSION"
    const val androidxTest = "androidx.test:core:$ANDROIDX_TEST_VERSION"
    const val androidxTestJunit = "androidx.test.ext:junit:$ANDROIDX_TEST_JUNIT_VERSION"
    const val assertj = "org.assertj:assertj-core:$ASSERTJ_VERSION"
    const val awaitility = "org.awaitility:awaitility:$AWAITILITY_VERSION"
    const val coil = "io.coil-kt:coil:$COIL_VERSION"
    const val coilGif = "io.coil-kt:coil-gif:$COIL_VERSION"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINES_VERSION"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION"
    const val dexter = "com.karumi:dexter:$DEXTER_VERSION"
    const val drawabletoolbox = "com.github.duanhong169:drawabletoolbox:$DRAWABLETOOLBOX_VERSION"
    const val dotenv = "io.github.cdimascio:java-dotenv:$DOTENV_VERSION"
    const val espressoCore = "androidx.test.espresso:espresso-core:$ESPRESSO_VERSION"
    const val exoplayerCore = "com.google.android.exoplayer:exoplayer-core:$EXOPLAYER_VERSION"
    const val exoplayerDash = "com.google.android.exoplayer:exoplayer-dash:$EXOPLAYER_VERSION"
    const val exoplayerHls = "com.google.android.exoplayer:exoplayer-hls:$EXOPLAYER_VERSION"
    const val exoplayerSmoothstreaming = "com.google.android.exoplayer:exoplayer-smoothstreaming:$EXOPLAYER_VERSION"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:$FIREBASE_ANALYTICS_VERSION"
    const val firebaseCore = "com.google.firebase:firebase-core:$FIREBASE_CORE_VERSION"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:$FIREBASE_CRASHLYTICS_VERSION"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging:$FIREBASE_MESSAGING_VERSION"
    const val firebasePlugin = "com.google.firebase:firebase-crashlytics-gradle:$FIREBASE_PLUGIN_VERSION"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:$FRAGMENT_KTX_VERSION"
    const val fresco = "com.facebook.fresco:fresco:$FRESCO_VERSION"
    const val gitversionerPlugin = "com.pascalwelsch.gitversioner:gitversioner:$GITVERSIONER_VERSION"
    const val glide = "com.github.bumptech.glide:glide:$GLIDE_VERSION"
    const val glideCompiler = "com.github.bumptech.glide:compiler:$GLIDE_VERSION"
    const val glideRedirect = "com.aminography:redirectglide:$GLIDE_REDIRECT_VERSION"
    const val googleServicesPlugin = "com.google.gms:google-services:$GOOGLE_SERVICES_VERSION"
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:$GRADLE_VERSIONS_PLUGIN"
    const val gson = "com.google.code.gson:gson:$GSON_VERSION"
    const val jacoco = "com.hiya:jacoco-android:$JACOCO_VERSION"
    const val json = "org.json:json:$JSON_VERSION"
    const val junit4 = "junit:junit:$JUNIT4_VERSION"
    const val junitJupiterApi = "org.junit.jupiter:junit-jupiter-api:$JUNIT5_VERSION"
    const val junitJupiterParams = "org.junit.jupiter:junit-jupiter-params:$JUNIT5_VERSION"
    const val junitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine:$JUNIT5_VERSION"
    const val junitVintageEngine = "org.junit.vintage:junit-vintage-engine:$JUNIT5_VERSION"
    const val keyboardVisibilityEvent = "net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:$KEYBOARD_VISIBILITY_EVENT_VERSION"
    const val kfixture = "com.flextrade.jfixture:kfixture:$KFIXTURE_VERSION"
    const val kluent = "org.amshove.kluent:kluent:$KLUENT_VERSION"
    const val koinAndroidxExt = "org.koin:koin-androidx-ext:$KOIN_VERSION"
    const val koinAndroidxScope = "org.koin:koin-androidx-scope:$KOIN_VERSION"
    const val koinAndroidxViewmodel = "org.koin:koin-androidx-viewmodel:$KOIN_VERSION"
    const val koinCore = "org.koin:koin-core:$KOIN_VERSION"
    const val koinCoreExt = "org.koin:koin-core-ext:$KOIN_VERSION"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$KOTLIN_VERSION"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION"
    const val ktlintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:$KTLINT_VERSION"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:$LEAK_CANARY_VERSION"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"
    const val materialComponents = "com.google.android.material:material:$MATERIAL_COMPONENTS_VERSION"
    const val marwonCore = "io.noties.markwon:core:$MARWON_VERSION"
    const val marwonLinkify = "io.noties.markwon:linkify:$MARWON_VERSION"
    const val marwonextStrikethrough = "io.noties.markwon:ext-strikethrough:$MARWON_VERSION"
    const val marwonImage = "io.noties.markwon:image:$MARWON_VERSION"
    const val mockito = "org.mockito:mockito-core:$MOKITO_VERSION"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:$MOKITO_KOTLIN_VERSION"
    const val navigationFragmentKTX = "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION"
    const val navigationSafeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$NAVIGATION_VERSION"
    const val navigationRuntimeKTX = "androidx.navigation:navigation-runtime-ktx:$NAVIGATION_VERSION"
    const val navigationUIKTX = "androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION"
    const val ok2curl = "com.github.mrmike:ok2curl:$OK2CURL_VERSION"
    const val okhttp = "com.squareup.okhttp3:okhttp:$OKHTTP_VERSION"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"
    const val okhttpMockWebserver = "com.squareup.okhttp3:mockwebserver:$OKHTTP_VERSION"
    const val photodraweeview = "me.relex:photodraweeview:$PHOTODRAWEEVIEW_VERSION"
    const val preferences = "androidx.preference:preference:$PREFERENCES_VERSION"
    const val recyclerview = "androidx.recyclerview:recyclerview:$RECYCLERVIEW_VERSION"
    const val retrofit = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"
    const val robolectric = "org.robolectric:robolectric:$ROBOLECTRIC_VERSION"
    const val roomCompiler = "androidx.room:room-compiler:$ROOM_VERSION"
    const val roomKtx = "androidx.room:room-ktx:$ROOM_VERSION"
    const val roomRxjava2 = "androidx.room:room-rxjava2:$ROOM_VERSION"
    const val roomRuntime = "androidx.room:room-runtime:$ROOM_VERSION"
    const val roomTesting = "androidx.room:room-testing:$ROOM_VERSION"
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:2.2.14"
    const val rxjava2Android = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val stetho = "com.facebook.stetho:stetho:$STETHO_VERSION"
    const val stethoOkhttp = "com.facebook.stetho:stetho-okhttp3:$STETHO_VERSION"
    const val timber = "com.jakewharton.timber:timber:$TIMBER_VERSION"
    const val truth = "com.google.truth:truth:$TRUTH_VERSION"
    const val workRuntimeKtx = "androidx.work:work-runtime-ktx:$WORK_VERSION"
    const val workTesting = "androidx.work:work-testing:$WORK_VERSION"

    @JvmStatic
    fun isNonStable(version: String): Boolean = isStable(version).not()

    @JvmStatic
    fun isStable(version: String): Boolean = ("^[0-9.]+$").toRegex().matches(version)
}
