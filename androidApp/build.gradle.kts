import java.text.SimpleDateFormat
import java.util.Calendar

plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}
android.buildFeatures.buildConfig=true
kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.alpha.showcase.android"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    val date = SimpleDateFormat("yyyyMMddHHmm")
    val formattedDate = date.format(Calendar.getInstance().time)
    defaultConfig {
        applicationId = "com.alpha.showcase.android"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = (findProperty("showcase.versionCode")as String).toInt()
        versionName = findProperty("showcase.versionName") as String
        setProperty(
            "archivesBaseName",
            "showcase-android-$versionCode($versionName)${formattedDate}"
        )
    }

    applicationVariants.configureEach {
        outputs.configureEach {
            (this as? com.android.build.gradle.internal.api.ApkVariantOutputImpl)?.outputFileName =
                "showcase-android.${versionName}_${versionCode}-${formattedDate}-${name}.apk"
        }
    }

    bundle {
        language {
            // Specify a list of split dimensions for language splits
            enableSplit = true
        }
        density {
            // Specify a list of split dimensions for density splits
            enableSplit = true
        }
        abi {
            // Specify a list of split dimensions for ABI splits
            enableSplit = true
        }
    }

    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
    }

    sourceSets {
        all {
            jniLibs.srcDirs(arrayOf("lib"))
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            isUniversalApk = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
