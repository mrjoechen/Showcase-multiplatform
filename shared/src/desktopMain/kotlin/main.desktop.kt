import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import java.awt.Desktop
import java.io.File
import java.util.UUID as JvmUUID
import java.lang.Process as JvmProcess

actual fun getPlatformName(): String =
    if (AppConfig.isWindows()) {
        "Windows"
    } else if (AppConfig.isMac()) {
        "macOs"
    } else
        "Desktop-" + AppConfig.getPlatformName()

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