import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

actual fun getPlatformName(): String = PLATFORM_ANDROID

lateinit var androidApp: Context

@Composable fun MainView() = App()

actual typealias UUID = java.util.UUID

actual fun randomUUID(): UUID = UUID.randomUUID()

actual typealias Process = java.lang.Process

actual fun showcaseConfigPath(): String = androidApp.filesDir.absolutePath

actual fun log(msg: String) {
    android.util.Log.d("Showcase-Android", msg)
}

actual fun rclonePath(): String = androidApp.applicationInfo.nativeLibraryDir + "/$NATIVE_LIB_NAME"

actual fun openScreenSaverSettings() {

}

@Composable
actual fun LottieAnimation(lottieAnimString: String, modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(lottieAnimString))
    com.airbnb.lottie.compose.LottieAnimation(
        composition,
        modifier = modifier,
        iterations = Int.MAX_VALUE
    )
}
