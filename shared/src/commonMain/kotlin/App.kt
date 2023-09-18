import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alpha.showcase.common.ui.MainNavHost
import com.alpha.showcase.common.ui.theme.MyApplicationTheme

@Composable
fun App() {
    MyApplicationTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            MainNavHost()
        }
    }
}