import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import org.jetbrains.skia.Rect
import org.jetbrains.skia.skottie.Animation
import org.jetbrains.skia.sksg.InvalidationController
import java.awt.Desktop
import java.io.File
import kotlin.math.roundToInt
import java.util.UUID as JvmUUID
import java.lang.Process as JvmProcess

actual fun getPlatformName(): String =
    if (AppConfig.isWindows()) {
        PLATFORM_WINDOWS
    } else if (AppConfig.isMac()) {
        PLATFORM_MACOS
    } else
        "$PLATFORM_DESKTOP-" + AppConfig.getPlatformName()

@Suppress("NewApi")
@Composable
fun MainView() {
    App()

//    DisposableEffect(Unit) {
//
//        var rProcess: JvmProcess? = null
//        println("AppPreview")
//
//        val appSupportPath = Paths.get(AppConfig.getConfigDirectory())
//        if (Files.notExists(appSupportPath)) {
//            Files.createDirectories(appSupportPath)
//        }
//        val resourcesDir = File(System.getProperty("compose.application.resources.dir"))
//        println(resourcesDir)
//
//        thread {
//            rProcess?.destroy()
//            rProcess = ProcessBuilder(resourcesDir.resolve("rclone").absolutePath, "serve", "http", "--addr", ":12121", "catnas:").start()
//            rProcess!!.inputStream.bufferedReader().readLine()
//            rProcess!!.waitFor()
//        }
//
//        onDispose {
//            println("AppPreview onDispose")
//            rProcess?.destroy()
//        }
//    }
}

@Suppress("NewApi")
@Preview
@Composable
fun AppPreview() {
    App()
}

actual typealias UUID = JvmUUID

actual fun randomUUID(): UUID = JvmUUID.randomUUID()
actual typealias Process = JvmProcess

actual fun showcaseConfigPath(): String {
    return AppConfig.getConfigDirectory()
}

actual fun rclonePath(): String {
    return File(System.getProperty("compose.application.resources.dir")).resolve("rclone").absolutePath
}

actual fun log(msg: String) {
    println(msg)
}

actual fun openScreenSaverSettings() {

    if (AppConfig.isWindows()){
        try {
            Runtime.getRuntime().exec("rundll32.exe shell32.dll,Control_RunDLL desk.cpl,,1");
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    if (AppConfig.isMac()){
        try {
            val path = "/System/Library/PreferencePanes/DesktopScreenEffectsPref.prefPane"
            Desktop.getDesktop().open(File(path))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


//https://github.com/JetBrains/compose-multiplatform-core/blob/jb-main/compose/mpp/demo/src/commonMain/kotlin/androidx/compose/mpp/demo/LottieAnimation.kt
@Composable
actual fun LottieAnimation(lottieAnimString: String, modifier: Modifier) {
    // Please note that it's NOT a part of Compose itself, but API of unstable skiko library that is used under the hood.
    // See:
    // - https://github.com/JetBrains/compose-multiplatform/issues/362
    // - https://github.com/JetBrains/compose-multiplatform/issues/3152
    val animation = Animation.makeFromString(lottieAnimString)
    val infiniteTransition = rememberInfiniteTransition()
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = animation.duration,
        animationSpec = infiniteRepeatable(
            animation = tween((animation.duration * 1000).roundToInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val invalidationController = remember { InvalidationController() }

    /*
     FIXME: https://github.com/JetBrains/compose-multiplatform/issues/3149
      Animation type doesn't trigger re-drawing the canvas because of incorrect detection
      "stability" of external types.
      Adding _any_ mutable state into `drawIntoCanvas` scope resolves the issue.

      Workaround for iOS/Web: move this line into `drawIntoCanvas` block.
     */
    animation.seekFrameTime(time, invalidationController)
    Canvas(modifier) {
        drawIntoCanvas {
            animation.render(
                canvas = it.nativeCanvas,
                dst = Rect.makeWH(size.width, size.height)
            )
        }
    }
}