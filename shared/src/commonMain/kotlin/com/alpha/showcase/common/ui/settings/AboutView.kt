package com.alpha.showcase.common.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleUp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalUriHandler
import com.alpha.showcase.common.BuildKonfig
import com.alpha.showcase.common.ui.view.IconItem
import com.alpha.showcase.common.ui.view.TextTitleMedium


/**
 *  - About
 *      - Readme
 *      - License
 *      - ChangeLog
 *      - Telegram
 *      - Thanks (Resources and Open Source Libraries)
 *      - Rate
 *      - Feedback
 *      - Donate
 *      - Version
 */


private const val app_page = "https://mrjoechen.github.io/ShowcaseApp/index"

private const val resUrl = "https://github.com/mrjoechen/ShowcaseApp/blob/main/README.md"
private const val telegramChannelUrl = "https://t.me/showcase_app_release"
private const val privacyPolicyUrl = "https://mrjoechen.github.io/ShowcaseApp/privacypolicy"

private const val TAG = "AboutPage"

const val GPL_V3 = "GNU General Public License v3.0"
const val GPL_V2 = "GNU General Public License v2.0"
const val APACHE_V2 = "Apache License 2.0"
const val MIT = "MIT License"
const val UNLICENSE = "The Unlicense"
const val BSD = "BSD 3-Clause License"

@Composable
fun AboutView() {

    var showOpenSourceDialog by remember {
        mutableStateOf(false)
    }

    Column {
        val uriHandler = LocalUriHandler.current
        fun openUrl(url: String) {
            try{
                uriHandler.openUri(url)
            }catch (ex: Exception){
                ex.printStackTrace()
                // todo gen qrcode and show
            }
        }
        TextTitleMedium(text = "About")


        IconItem(
            Icons.Outlined.Info,
            desc = "Info",
            onClick = {
                openUrl(resUrl)
            }){

            Text(
                text = "${BuildKonfig.versionName}.${BuildKonfig.gitHash}(${BuildKonfig.versionCode})",
                color = LocalContentColor.current.copy(0.6f)
            )
        }

        IconItem(
            "icons8_telegram_app.xml",
            desc = "Telegram",
            onClick = {
                openUrl(telegramChannelUrl)
            })

//        IconItem(
//            Icons.Outlined.Feedback,
//            desc = stringResource(R.string.feedback),
//            onClick = {
//                openUrl("feedbackUrl")
//            })

        IconItem(
            Icons.Outlined.TipsAndUpdates,
            desc = "Thanks",
            onClick = {
                showOpenSourceDialog = !showOpenSourceDialog
            })

        IconItem(
            Icons.Outlined.PrivacyTip,
            desc = "Privacy Policy",
            onClick = {
                openUrl(privacyPolicyUrl)
            })

//        SwitchItem(
//            Icons.Outlined.Autorenew,
//            false,
//            stringResource(R.string.auto_update)
//        ) {
//
//        }
//

        IconItem(
            Icons.Outlined.ThumbUp,
            desc = "Rate us",
            onClick = {


            }
        )

        IconItem(
            Icons.Outlined.ArrowCircleUp,
            desc = "update",
            onClick = {
//                coroutineScope.launch {
//                    UpdateExt.checkForUpdate {
//                        UpdateExt.downloadApk(App.instance, it.apkUrl, it.version)
//                            .collect { downloadStatus ->
//                                if (downloadStatus is DownloadStatus.Finished) {
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                        App.instance.installLatestApk()
//                                    }
//                                }
//                            }
//                    }
//                }


            }
        )

//        IconItem(
//            Icons.Outlined.IosShare,
//            desc = "Share",
//            onClick = {
//
//            }
//        )

//        IconItem(
//            icon = Icons.Outlined.NewReleases,
//            desc = stringResource(id = R.string.membership),
//            onClick = {
//                openBottomBilling = !openBottomBilling
//            })
//
//        MemberBillingList(openBottomBilling){
//            openBottomBilling = false
//        }


    }

    if (showOpenSourceDialog) {

    }

}


val openSourceLibraries = listOf(
    Triple("Accompanist", "https://github.com/google/accompanist", APACHE_V2),
    Triple("AndroidX", "https://developer.android.com/jetpack/androidx", APACHE_V2),
    Triple("AndroidX Work Manager", "https://developer.android.com/topic/libraries/architecture/workmanager", APACHE_V2),
    Triple("Compose", "https://developer.android.com/jetpack/compose", APACHE_V2),
    Triple("Coil", "https://github.com/coil-kt/coil", APACHE_V2),
    Triple("Google Android Material", "https://github.com/material-components/material-components-android", APACHE_V2),
    Triple("Hilt", "https://github.com/google/dagger/tree/main/java/dagger/hilt/android", APACHE_V2),
    Triple("Icons8", "https://icons8.com/", "Universal Multimedia Licensing Agreement for Icons8"),
    Triple("Kotlinx Coroutines", "https://github.com/Kotlin/kotlinx.coroutines", APACHE_V2),
    Triple("Kotlinx Serialization JSON", "https://github.com/Kotlin/kotlinx.serialization", APACHE_V2),
    Triple("Landscape", "https://github.com/skydoves/landscape", APACHE_V2),
    Triple("LeakCanary", "https://square.github.io/leakcanary", APACHE_V2),
    Triple("Lottie", "https://github.com/airbnb/lottie-android", APACHE_V2),
    Triple("Microsoft App Center", "https://github.com/microsoft/appcenter-sdk-android", MIT),
    Triple("OkHttp", "https://square.github.io/okhttp", APACHE_V2),
    Triple("Okio", "https://square.github.io/okio", APACHE_V2),
    Triple("Once", "https://github.com/jonfinerty/Once", APACHE_V2),
    Triple("ProtoBuf", "https://github.com/protocolbuffers/protobuf", APACHE_V2),
    Triple("Retrofit", "https://square.github.io/retrofit/", APACHE_V2),
    Triple("Timber", "https://github.com/JakeWharton/timber", APACHE_V2),
    Triple("Toasty", "https://github.com/GrenderG/Toasty", APACHE_V2),
)