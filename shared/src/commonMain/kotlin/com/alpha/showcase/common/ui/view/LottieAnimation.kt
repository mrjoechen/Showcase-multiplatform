package com.alpha.showcase.common.ui.view
/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import LottieAnimation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource


@Composable
fun LabeledAnimation(label: String, lottieAsset: String) {
  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
    Column(
      modifier = Modifier
        .wrapContentSize()
        .align(Alignment.Center),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = label, color = MaterialTheme.colorScheme.primary)
//      LottieAssetLoader(lottieAsset, repeat)

      Row(modifier = Modifier
        .fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1f))
        LottieAssetLoader(lottieAsset, Modifier.weight(1f).fillMaxSize())
        Spacer(modifier = Modifier.weight(1f))
      }
    }
  }

}

@Composable
fun LabeledAnimation(
  msg: String = "",
  lottieAsset: String,
  modifier: Modifier = Modifier
) {
  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Column(
      modifier = Modifier
        .wrapContentSize()
        .align(Alignment.Center),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      if (msg.isNotBlank()) {
        Text(
          text = msg,
          color = MaterialTheme.colorScheme.primary,
          textAlign = TextAlign.Center
        )
      }

      Row(modifier = Modifier
        .fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1f))
        LottieAssetLoader(lottieAsset, modifier.weight(1f))
        Spacer(modifier = Modifier.weight(1f))
      }
    }
  }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LottieAssetLoader(lottieAsset: String, modifier: Modifier = Modifier.fillMaxSize()) {
  val density = LocalDensity.current
  val res = remember(lottieAsset, density) {
    runBlocking {
      String(resource(lottieAsset).readBytes()).trim()
    }
  }
  LottieAnimation(res, modifier)
}