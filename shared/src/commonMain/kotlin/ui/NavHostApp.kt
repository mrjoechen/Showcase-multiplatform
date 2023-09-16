package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

sealed class Screen(val route: String, val icon: ImageVector) {
  data object Sources : Screen(StringResources.current.sources,  Icons.Outlined.Folder)
  data object Settings : Screen(StringResources.current.settings,  Icons.Outlined.Settings)
}

val navItems = listOf(
  Screen.Sources,
  Screen.Settings,
)

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
                .fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {

              Surface(
                Modifier.padding(10.dp),
                shape = RoundedCornerShape(6.dp),
              ) {

                Box(modifier = Modifier.clickable {
                  currentDestination = Screen.Sources.route
                }) {
                  Text(
                    modifier = Modifier.padding(30.dp, 10.dp),
                    text = StringResources.current.app_name,
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis, color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                  )
                }

              }


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
                    shadowElevation = if (settingSelected) 1.dp else 0.dp
                ) {
                    Box(modifier = Modifier
                        .clickable {
                          settingSelected = !settingSelected
                          if (settingSelected){
                            currentDestination = Screen.Settings.route
                          }else {
                            currentDestination = Screen.Sources.route
                          }
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

    },
//    bottomBar = {
//      Column {
//        NavigationBar {
//          navItems.forEachIndexed {_, item ->
//            NavigationBarItem(
//              icon = {Icon(item.icon, contentDescription = item.route)},
//              label = {Text(item.route)},
//              selected = currentDestination == item.route,
//              onClick = {
//                currentDestination = item.route
//              }
//            )
//          }
//        }
//      }
//    }
  ){
    Column {
      Spacer(Modifier.height(it.calculateTopPadding()))
      if (currentDestination == Screen.Sources.route) {
        ProgressIndicator()
      }
      if (currentDestination == Screen.Settings.route) {
        SettingsListView(SettingsViewModel())
      }
    }

  }
}
