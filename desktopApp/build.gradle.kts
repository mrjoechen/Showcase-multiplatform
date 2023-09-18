import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}
apply(from = "../version.gradle.kts")

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.flatlaf)
                implementation(project(":shared"))
            }
        }
    }
}

compose.desktop {
    application {
        project.version = project.extra["versionCode"].toString()
        mainClass = "Showcase"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "Showcase"
            packageVersion = project.extra["versionName"] as String
            version = project.version
            description = "Showcase App"
            copyright = "© 2023 Joe Chen. All rights reserved."
            vendor = "GitHub"
//            licenseFile.set(project.file("LICENSE.txt"))
            // 设置 resources 拷贝到本地
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))

            val iconsRoot = project.file("resources")
            macOS {
                // macOS specific options
                iconFile.set(iconsRoot.resolve("Showcase.icns"))
                bundleID = "com.alpha.showcase.macos"
                dockName = "Showcase App"
                dmgPackageVersion = project.version.toString()
                pkgPackageVersion = project.version.toString()
            }
            windows {
                // Windows specific options
                iconFile.set(iconsRoot.resolve("Showcase.ico"))
                menuGroup = "Showcase App"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "18159995-d967-4CD2-8885-77BFA97CFA9F"
            }
            linux {
                // Linux specific options
                iconFile.set(iconsRoot.resolve("Showcase.png"))
            }
        }
    }
}


