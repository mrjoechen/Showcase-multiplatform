import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import java.awt.Button
import java.awt.Desktop
import java.io.File
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.concurrent.thread
import java.util.UUID as JvmUUID
import java.lang.Process as JvmProcess

actual fun getPlatformName(): String = "Desktop"

@Suppress("NewApi")
@Composable
fun MainView() {
    Column {
        Button(onClick = {
            openScreenSaverSettings()
        }) {
            Text("Click me")
        }
        App()
    }

    DisposableEffect(Unit) {

        var rProcess: JvmProcess? = null
        println("AppPreview")

        val appSupportPath = Paths.get(AppConfig.getConfigDirectory())
        if (Files.notExists(appSupportPath)) {
            Files.createDirectories(appSupportPath)
        }
        val resourcesDir = File(System.getProperty("compose.application.resources.dir"))
        println(resourcesDir)

        thread {
            rProcess?.destroy()
            rProcess = ProcessBuilder(resourcesDir.resolve("rclone").absolutePath, "serve", "http", "--addr", ":12121", "google:").start()
            rProcess!!.inputStream.bufferedReader().readLine()
            rProcess!!.waitFor()
        }

        onDispose {
            println("AppPreview onDispose")
            rProcess?.destroy()
        }
    }
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
actual fun openScreenSaverSettings() {

    if (AppConfig.isWindows()){
        try {
            // 使用Java的Desktop类打开Windows的屏保设置
            Desktop.getDesktop().browse(URI("control:/name Microsoft.ScreenSaver"))
        } catch (e: Exception) {
            // 处理错误，例如Desktop类不支持此操作
            e.printStackTrace()
        }
    }

    if (AppConfig.isMac()){
        try {
            // 使用Java的Desktop类打开macOS的屏保设置
            val path = "/System/Library/PreferencePanes/DesktopScreenEffectsPref.prefPane"
            Desktop.getDesktop().open(File(path))
        } catch (e: Exception) {
            // 处理错误，例如Desktop类不支持此操作
            e.printStackTrace()
        }
    }

}