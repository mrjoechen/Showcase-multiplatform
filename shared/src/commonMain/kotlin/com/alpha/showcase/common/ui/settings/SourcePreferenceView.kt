package com.alpha.showcase.common.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material.icons.outlined.AutoMode
import androidx.compose.runtime.*
import com.alpha.showcase.common.data.Settings
import com.alpha.showcase.common.ui.view.SwitchItem
import com.alpha.showcase.common.ui.view.TextTitleMedium


/**
 *  - Source Preference
 *      - Sort (Random, Name Asc, Name Desc， Asc Date, Desc Date)
 *      - Recursive Source folder
 *      - Auto open latest source
 *      - Auto Refresh
 *
 */
@Composable
fun SourcePreferenceView(
    settings: Settings = Settings.getDefaultInstance(),
    onSet: (String, Any) -> Unit
) {

    Column {

        TextTitleMedium(text = "Source Preference")

        SwitchItem(
            Icons.Outlined.AccountTree,
            check = settings.recursiveDirContent,
            desc = "Recursive source folder",
            onCheck = {
                onSet(SourcePreferenceItem.RecursiveDir, it)
            })

//        SwitchItem(
//            Icons.Outlined.Refresh,
//            check = settings.autoRefresh,
//            desc = stringResource(R.string.auto_refresh_source_content),
//            onCheck = {
//                onSet(SourcePreferenceItem.AutoRefresh, it)
//            })
//
//        SwitchItem(
//            Icons.Outlined.VideoFile,
//            check = settings.supportVideo,
//            desc = stringResource(R.string.contain_video),
//            onCheck = {
//                onSet(SourcePreferenceItem.SupportVideo, it)
//            })

        SwitchItem(
            Icons.Outlined.AutoMode,
            check = settings.autoOpenLatestSource,
            desc = "Auto open latest source",
            onCheck = {
                onSet(SourcePreferenceItem.AutoOpenLatestSource, it)
            })

    }

}

sealed class SourcePreferenceItem {

    companion object {
        const val RecursiveDir: String = "RecursiveDir"
        const val AutoOpenLatestSource: String = "AutoOpenLatestSource"
        const val SupportVideo: String = "SupportVideo"
        const val AutoRefresh: String = "AutoRefresh"
    }
}