import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

expect fun getPlatformName(): String


const val TEST_KEY = "1234567890123456"
const val TEST_IV = "0123456789abcdef"// 长度必须是 16 个字节

const val PLATFORM_ANDROID = "Android"
const val PLATFORM_IOS = "iOS"
const val PLATFORM_DESKTOP = "Desktop"
const val PLATFORM_WINDOWS = "Windows"
const val PLATFORM_MACOS = "macOS"


fun isAndroid(): Boolean {
    return getPlatformName() == PLATFORM_ANDROID
}

fun isWindows(): Boolean {
    return getPlatformName() == PLATFORM_WINDOWS
}

fun isMacOS(): Boolean {
    return getPlatformName() == PLATFORM_MACOS
}

fun isDesktop(): Boolean {
    return getPlatformName().contains(PLATFORM_DESKTOP) or isWindows() or isMacOS()
}


expect class UUID {
    override fun toString(): String
}

expect fun randomUUID(): UUID

expect fun log(msg: String)

expect fun String.encodeName(): String

expect fun String.decodeName(): String

expect fun String.encodePass(key: String, iv: String): String

expect fun String.decodePass(key: String, iv: String): String

expect abstract class Process

expect fun openScreenSaverSettings()

expect fun rclonePath(): String

expect fun showcaseConfigPath(): String

@Composable
expect fun LottieAnimation(lottieAnimString: String, modifier: Modifier = Modifier.fillMaxSize())