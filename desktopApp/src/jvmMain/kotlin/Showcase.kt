import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

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

        Window(onCloseRequest = {
            rProcess?.destroy()
            exitApplication()
        }, icon = icon, title = "Showcase") {
            MainView()

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

