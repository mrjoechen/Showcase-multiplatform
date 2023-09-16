import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import ui.LocalStringResources

class Showcase{
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Showcase().main()
        }
    }

    fun main() = application {

        var rProcess: java.lang.Process? = null
        val icon = painterResource("showcase_logo.png")

        Window(
            onCloseRequest = {
                rProcess?.destroy()
                exitApplication()
            },
            icon = icon,
            title = LocalStringResources.current.app_name,
            state = WindowState(width = 1280.dp, height = 720.dp),
        ) {

            Column(modifier = Modifier.sizeIn(640.dp, 360.dp)) {
                MainView()
            }

            //        val appSupportPath = Paths.get(AppConfig.getConfigDirectory())
            //        if (Files.notExists(appSupportPath)) {
            //            Files.createDirectories(appSupportPath)
            //        }
            //        val resourcesDir = File(System.getProperty("compose.application.resources.dir"))
            //        println(resourcesDir)
            //
            //        thread {
            //            rProcess?.destroy()
            //            rProcess = ProcessBuilder(resourcesDir.resolve("rclone").absolutePath, "serve", "http", "--addr", ":12121", "google:").start()
            //            rProcess!!.inputStream.bufferedReader().readLine()
            //            rProcess!!.waitFor()
            //        }

        }
    }
}

