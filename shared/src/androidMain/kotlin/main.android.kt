import android.content.Context
import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Android"

lateinit var app: Context

@Composable fun MainView() = App()

actual typealias UUID = java.util.UUID

actual fun randomUUID(): UUID = UUID.randomUUID()

actual typealias Process = java.lang.Process

actual fun showcaseConfigPath(): String = app.filesDir.absolutePath

actual fun openScreenSaverSettings() {

}