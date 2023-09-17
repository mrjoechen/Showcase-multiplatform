package com.alpha.showcase.common.data

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    //
    val showTimeAndDate: Boolean = true,
    val fadeMode: FadeMode = FadeMode(),
    val slideMode: SlideMode = SlideMode(),
    val frameWallMode: FrameWallMode = FrameWallMode(),

    val showcaseMode: Int = 0,
    val autoOpenLatestSource: Boolean = false,
    val recursiveDirContent: Boolean = false,
    val containsVideo: Boolean = false,
) {

    companion object {
        fun getDefaultInstance(): Settings {
            return Settings()
        }
    }

    @Serializable
    data class SlideMode(
        val intervalTimeUnit: Int = 0,
        val intervalTime: Int = 0,
        val showTimeProgressIndicator: Boolean = false,
        val orientation: Int = 0,
        val displayMode: Int = 0
    )

    @Serializable
    class FadeMode(
        val displayMode: Int = 0,
        val intervalTimeUnit: Int = 0,
        val intervalTime: Int = 0,
        val showTimeProgressIndicator: Boolean = false,
    )

    @Serializable
    class FrameWallMode(
        val interval: Int = 5,
        val matrixSizeRows: Int = 1,
        val matrixSizeColumns: Int = 2
    )
}