plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
    kotlin("plugin.serialization").version(libs.versions.kotlin.get())
    id("org.lsposed.lsparanoid")
}

kotlin {
    androidTarget()

    jvm("desktop")

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "shared"
//            isStatic = true
//        }
//    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.molecule.runtime)
                implementation(libs.decompose)
                implementation(libs.decompose.compose.multiplatform)
                implementation(libs.decompose.router)
                implementation(libs.essenty.parcelable)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.qdsfdhvh.image.loader)
                implementation(libs.kstore)
                implementation(libs.kstore.file)
                implementation(libs.kotlinx.datetime)
                implementation(libs.lsposed.lsparanoid)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.androidx.activity.compose)
                api(libs.androidx.appcompat)
                api(libs.androidx.core.ktx)

                implementation(libs.ktor.client.cio)
                implementation(libs.kstore.file)
                implementation(libs.androidx.compose.windowsizeclass)
            }
        }
//        val iosX64Main by getting
//        val iosArm64Main by getting
//        val iosSimulatorArm64Main by getting
//        val iosMain by creating {
//            dependsOn(commonMain)
//            iosX64Main.dependsOn(this)
//            iosArm64Main.dependsOn(this)
//            iosSimulatorArm64Main.dependsOn(this)
//            dependencies {
//                implementation(libs.ktor.client.darwin)
//                implementation(libs.kstore.file)
//            }
//        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(libs.ktor.client.cio)
                implementation(libs.kstore.file)
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.alpha.showcase.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

lsparanoid {
    seed = null
    global = false
    includeDependencies = true
    variantFilter = { variant ->
        val b = variant.name.contains("Release")
                || variant.name.contains("release")
                || variant.name.contains("Beta")
                || variant.name.contains("beta")
        println("lsparanoid variantFilter: ${variant.name} $b")
        b
    }
}
