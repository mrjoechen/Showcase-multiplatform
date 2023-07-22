package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.settings.ProgressIndicator
import ui.settings.SettingsListView
import ui.settings.SettingsViewModel


/**
 * Created by chenqiao on 2022/12/25.
 * e-mail : mrjctech@gmail.com
 */

sealed class Screen(val route: String, val resourceId: Int, val icon: ImageVector) {
  object Sources : Screen("Sources", 1, Icons.Outlined.Folder)
  object Settings : Screen("Settings", 2, Icons.Outlined.Settings)
}

val navItems = listOf(
  Screen.Sources,
  Screen.Settings,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost() {

  var currentDestination by remember {
      mutableStateOf(Screen.Sources.route)
  }
  Scaffold(
    modifier = Modifier
        .fillMaxSize(),
    topBar = {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 5.dp), horizontalAlignment = Alignment.Start){

            Row(
                Modifier
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, ) {
                Text(
                    modifier = Modifier.padding(30.dp, 0.dp),
                    text = "Showcase",
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis, color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                var settingSelected by remember {
                    mutableStateOf(false)
                }.apply {
                    value = Screen.Settings.route == currentDestination
                }

                Surface(
                    Modifier.padding(20.dp, 0.dp),
                    shape = RoundedCornerShape(6.dp),
                    tonalElevation = if (settingSelected) 1.dp else 0.dp,
                    shadowElevation = if (settingSelected) 1.dp else 0.dp,
//                    border = if (settingSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null
                ) {
                    Box(modifier = Modifier
                        .clickable {
                            settingSelected = !settingSelected

                        }
                        .padding(10.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = Screen.Settings.route,
                            tint = if (settingSelected) MaterialTheme.colorScheme.primary else LocalContentColor.current
                        )
                    }

                }

            }

        }

    }, bottomBar = {
          Column {
              NavigationBar {
                  navItems.forEachIndexed { _, item ->
                      NavigationBarItem(
                          icon = {Icon(item.icon, contentDescription = item.route)},
                          label = {Text(item.route)},
                          selected = currentDestination == item.route,
                          onClick = {
                              currentDestination = item.route
                          }
                      )
                  }
              }
          }
      }){


      Column {
          Spacer(Modifier.height(it.calculateTopPadding()))
          if (currentDestination == Screen.Sources.route){
              ProgressIndicator()
          }else {
              SettingsListView(SettingsViewModel())
          }
      }


  }
}
