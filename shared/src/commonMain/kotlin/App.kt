import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.MainNavHost

@Composable
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            MainNavHost()
        }
    }

}

expect fun getPlatformName(): String


const val TEST_KEY = "1234567890123456"
const val TEST_IV = "0123456789abcdef"// 长度必须是 16 个字节

expect class UUID {
    override fun toString(): String
}

expect fun randomUUID(): UUID

expect fun String.encodeName(): String

expect fun String.decodeName(): String

expect fun String.encodePass(key: String, iv: String): String

expect fun String.decodePass(key: String, iv: String): String

expect abstract class Process

expect fun openScreenSaverSettings()

