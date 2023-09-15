import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.utils.io.core.toByteArray
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.MainNavHost

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Hello, ${getPlatformName()}"
                showImage = !showImage
            }) {
                Text(greetingText)
            }
            AnimatedVisibility(showImage) {
                Image(
                    painterResource("compose-multiplatform.xml"),
                    null
                )
            }

            MainNavHost()
        }
    }

}

expect fun getPlatformName(): String


val TEST_KEY = "1234567890123456"
val TEST_IV = "0123456789abcdef"// 长度必须是 16 个字节

expect class UUID {
    override fun toString(): String
}

expect fun randomUUID(): UUID

expect fun String.encodeName(): String

expect fun String.decodeName(): String

expect fun String.encodePass(key: String, iv: String): String

expect fun String.decodePass(key: String, iv: String): String

expect abstract class Process

// Shared module (commonMain)
expect fun openScreenSaverSettings()

