import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = App()

actual typealias UUID = java.util.UUID

actual fun randomUUID(): UUID = UUID.randomUUID()

actual typealias Process = java.lang.Process