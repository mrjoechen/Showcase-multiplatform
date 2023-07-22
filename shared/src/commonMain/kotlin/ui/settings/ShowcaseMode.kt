package ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Style
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alpha.showcase.ui.settings.AboutView
import com.alpha.showcase.ui.settings.SlideModeView
import data.Settings
import ui.view.CheckItem
import ui.view.SwitchItem
import ui.view.TextTitleMedium

const val SHOWCASE_MODE_SLIDE = 0
const val SHOWCASE_MODE_FRAME_WALL = 1
const val SHOWCASE_MODE_FADE = 2
const val SHOWCASE_SCROLL_FADE = 3
const val SHOWCASE_SQUARE = 4

@Composable
fun ShowcaseSettings(
    settings: Settings = Settings.getDefaultInstance(),
    generalPreference: GeneralPreference = GeneralPreference(1, 0, ANONYMOUS_USAGE_DEFAULT),
    onSettingChanged: (Settings) -> Unit
) {

    val styleList = listOf(
        ShowcaseMode.Slide.toPairWithResString(),
        ShowcaseMode.FrameWall.toPairWithResString(),
        ShowcaseMode.Fade.toPairWithResString()
    )

    Column {
//        TextTitleMedium(text = stringResource(R.string.showcase_preview))
//        ElevatedCard(
//            modifier = Modifier
//                .padding(20.dp, 10.dp, 20.dp, 10.dp)
//                .fillMaxWidth(),
//            onClick = {  }, shape = MaterialTheme.shapes.small
//        ) {
//            Box(
//                Modifier
//                    .padding(4.dp)
//                    .fillMaxWidth()
//                    .aspectRatio(16f / 9f, matchHeightConstraintsFirst = true)) {
//                val contents = mutableListOf(R.mipmap.icon_app, R.mipmap.icon_app)
//                MainPlayScreen(contents, settings)
//            }
//        }


        Spacer(modifier = Modifier.height(10.dp))
        TextTitleMedium(text = "Style")

        CheckItem(
            when (settings.showcaseMode) {
                SHOWCASE_MODE_SLIDE -> {
                    Icons.Outlined.Style
                }
                SHOWCASE_MODE_FRAME_WALL -> {
                    Icons.Outlined.Style
                }
                else -> {
                    Icons.Outlined.Style
                }
             },
            ShowcaseMode.fromValue(settings.showcaseMode).toPairWithResString(),
            "Style",
            styleList,
            onCheck = {
            }
        )


        when (settings.showcaseMode) {
            SHOWCASE_MODE_SLIDE -> SlideModeView(settings.slideMode) { key, value ->
                val slideModeBuilder = settings.slideMode
                when (key) {

                    DisplayMode.key -> {
                    }
                    Orientation.key -> {
                    }
                    AutoPlay.key -> {
                    }
                    AutoPlayDuration.key -> {
                    }
                    IntervalTimeUnit.key -> {
                    }
                    ShowTimeProgressIndicator.key -> {
                    }
                    ShowContentMetaInfo.key -> {
                    }
                    SortRule.key -> {
                    }
                }
            }
            SHOWCASE_MODE_FRAME_WALL -> FrameWallModeView(settings.frameWallMode){key, value ->
                val frameWallMode = settings.frameWallMode
                when (key) {
                    FrameWallMode.key -> {
                    }

                    MatrixSize.Row -> {
                    }

                    MatrixSize.Column -> {
                    }

                    Interval.key -> {
                    }
                }


            }
            SHOWCASE_MODE_FADE -> FadeModeView(settings.fadeMode) { key, value ->

                val faceModeBuilder = settings.fadeMode
                when (key) {

                    DisplayMode.key -> {
                    }
                    AutoPlay.key -> {
                    }
                    AutoPlayDuration.key -> {
                    }
                    IntervalTimeUnit.key -> {
                    }
                    ShowTimeProgressIndicator.key -> {
                    }
                    ShowContentMetaInfo.key -> {
                    }
                    SortRule.key -> {
                    }
                }

            }

            SHOWCASE_SCROLL_FADE -> {
//                ScrollModeView()
            }

            else -> {

            }
        }

        SwitchItem(
            Icons.Outlined.AccessTime,
            check = settings.showTimeAndDate,
            desc = "Show time and date",
            onCheck = {

            }
        )

        GeneralView(generalPreference) { key, value ->
            when (key) {

                GeneralPreferenceItem.Language -> {

                }

                GeneralPreferenceItem.AnonymousUsage -> {
                }

                GeneralPreferenceItem.DarkMode -> {

                }

                GeneralPreferenceItem.CacheSize -> {
                }
                else ->{

                }
            }
        }

        SourcePreferenceView(settings){ key, value ->

            when(key){

                SourcePreferenceItem.RecursiveDir -> {
                }

                SourcePreferenceItem.AutoRefresh -> {
                }

                SourcePreferenceItem.AutoOpenLatestSource -> {
                }
                SourcePreferenceItem.SupportVideo -> {
                }

                else -> {

                }
            }

        }

        AboutView()

    }
}


sealed class ShowcaseMode(type: Int, title: String, resString: Int): Select<Int>(type, title, resString){
    object Slide: ShowcaseMode(SHOWCASE_MODE_SLIDE, "Slide", 1)
    object FrameWall: ShowcaseMode(SHOWCASE_MODE_FRAME_WALL, "Frame wall", 2)
    object Fade: ShowcaseMode(SHOWCASE_MODE_FADE, "Fade", 3)

    companion object {
        const val key: String = "ShowcaseMode"
        fun fromValue(type: Int): ShowcaseMode {
            return when(type){
                SHOWCASE_MODE_SLIDE -> Slide
                SHOWCASE_MODE_FRAME_WALL -> FrameWall
                SHOWCASE_MODE_FADE -> Fade
                else -> Slide
            }
        }
    }
}


fun getModeName(mode: Int): String{
    return when(mode){
        SHOWCASE_MODE_SLIDE -> ShowcaseMode.Slide.title
        SHOWCASE_MODE_FRAME_WALL -> ShowcaseMode.FrameWall.title
        SHOWCASE_MODE_FADE -> ShowcaseMode.Fade.title
//        SHOWCASE_SQUARE -> "Square"

        else -> {
            "Unknown"
        }
    }
}

