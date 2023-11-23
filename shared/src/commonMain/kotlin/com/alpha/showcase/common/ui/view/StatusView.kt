package com.alpha.showcase.common.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.alpha.showcase.common.ui.StringResources
import com.alpha.showcase.common.ui.theme.Dimen

@Composable
fun DataNotFoundAnim() {
    LabeledAnimation(StringResources.current.data_not_found, "lottie/lottie_error_screen.json")
}

@Composable
fun DataNotFoundAnim(msg: String) {
    LabeledAnimation(msg, "lottie/lottie_error_screen.json")
}

@Composable
fun UnderConstructionAnim() {
    LabeledAnimation(StringResources.current.data_under_construct, "lottie/lottie_building_screen.json")
}

@Composable
fun ProgressIndicator(size: Dp = Dimen.spaceXXL) {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(size)
        )
    }
}

@Composable
fun LoadingIndicator(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)) {
            Spacer(modifier = Modifier.weight(2f))
            LottieAssetLoader("lottie/lottie_loading.json", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(2f))
        }
    }
}
