package ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import data.Settings
import kotlinx.coroutines.delay


@Composable
fun SettingsListView(viewModel: SettingsViewModel) {
    var uiState by remember {
        mutableStateOf<UiState<Settings>>(UiState.Loading)
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        uiState.let {
            when (it) {
                UiState.Loading -> {
                    ProgressIndicator()
                }
                is UiState.Content -> SettingsColumn(
                    (uiState as UiState.Content<Settings>).data,
                    viewModel
                )

                else -> {}
            }


            LaunchedEffect(Unit){
                delay(1000)
                uiState = UiState.Content(Settings())
            }
        }
    }
}


@Suppress("UNCHECKED_CAST")
@Composable
fun SettingsColumn(settings: Settings, viewModel: SettingsViewModel) {

//    val settingState by remember(settings) {
//        mutableStateOf(settings)
//    }
//    val settingProvider by remember(settings) {
//        derivedStateOf {
//            settings.toBuilder()
//        }
//    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .widthIn(max = 650.dp)
    ) {

        ShowcaseSettings(settings, viewModel.getGeneralSettings(), onSettingChanged = {

        })
    }
}

class SettingsViewModel {
    fun getGeneralSettings(): GeneralPreference {

        return GeneralPreference(1, 1)
    }

}



interface BaseState
sealed interface UiState<out T> : BaseState {
    data class Content<out T>(val data: T) : UiState<T>
    object Loading : UiState<Nothing>
    data class Error(val msg: String? = "Error") : UiState<Nothing>
}

val UiState<*>.succeeded
    get() = this is UiState.Content && data != null


@Composable
fun ProgressIndicator(size: Dp = 30.dp) {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(size)
        )
    }
}