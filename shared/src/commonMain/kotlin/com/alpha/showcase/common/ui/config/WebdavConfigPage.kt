package com.alpha.showcase.common.ui.config

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alpha.networkfile.model.NetworkFile
import com.alpha.networkfile.storage.TYPE_WEBDAV
import com.alpha.networkfile.storage.WEBDAV
import com.alpha.networkfile.storage.getType
import com.alpha.networkfile.storage.remote.WebDav
import com.alpha.networkfile.util.RConfig
import com.alpha.showcase.common.ui.StringResources
import com.alpha.showcase.common.ui.theme.Dimen
import com.alpha.showcase.common.ui.view.HintText
import com.alpha.showcase.common.utils.checkName
import com.alpha.showcase.common.utils.checkPath
import com.alpha.showcase.common.utils.checkPort
import com.alpha.showcase.common.utils.checkUrl
import com.alpha.showcase.common.widget.PasswordInput
import com.alpha.showcase.common.widget.SelectPathDropdown
import decodeName
import encodeName
import kotlinx.coroutines.launch
import java.net.URI

@Composable
fun WebdavConfigPage(
  webDav: WebDav? = null,
  onTestClick: suspend (WebDav) -> Result<Any>?,
  onSaveClick: suspend (WebDav) -> Unit,
  onSelectPath: (suspend (WebDav, String) -> Result<Any>?)? = null
) {
  var name by rememberSaveable(key = "name") {
    mutableStateOf(webDav?.name?.decodeName() ?: "")
  }
  var url by rememberSaveable(key = "url") {
    mutableStateOf(webDav?.url ?: "")
  }
  var port by rememberSaveable(key = "port") {
    mutableStateOf(webDav?.port?.toString() ?: "")
  }
  var username by rememberSaveable(key = "username") {
    mutableStateOf(webDav?.user ?: "")
  }
  var password by rememberSaveable(key = "password") {
    mutableStateOf(webDav?.passwd?.let {RConfig.decrypt(it)} ?: "")
  }
  var path by rememberSaveable(key = "path") {
    mutableStateOf(webDav?.path ?: "")
  }

  var resultWebdav by remember {
    mutableStateOf(webDav)
  }

  val editMode = webDav != null

  var passwordVisible by rememberSaveable(key = "passwordVisible") {mutableStateOf(false)}
  var nameValid by rememberSaveable(key = "nameValid") {mutableStateOf(true)}
  var urlValid by rememberSaveable(key = "urlValid") {mutableStateOf(true)}
  var portValid by rememberSaveable(key = "portValid") {mutableStateOf(true)}
  var pathValid by rememberSaveable(key = "pathValid") {mutableStateOf(true)}

  val scope = rememberCoroutineScope()
  val label =
    "${StringResources.current.my} ${getType(TYPE_WEBDAV).typeName} ${StringResources.current.source}"
  val focusRequester = remember {FocusRequester()}

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(
        rememberScrollState()
      )
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
      modifier = Modifier.focusRequester(focusRequester),
      label = {Text(text = StringResources.current.source_name, style = TextStyle(fontWeight = FontWeight.Bold))},
      value = name,
      onValueChange = {
        name = it
        nameValid = checkName(it)
      },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
      placeholder = {HintText(text = label)},
      singleLine = true,
      isError = ! nameValid
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
      label = {Text(text = StringResources.current.Url, style = TextStyle(fontWeight = FontWeight.Bold))},
      value = url,
      onValueChange = {
        url = it
        urlValid = checkUrl(it)

        try {
          val uri = URI(it)
          val portMatches = uri.port == - 1 || uri.port in 1 .. 65535
          if (portMatches) {
            port = if (uri.port == - 1) {
              //              if (url[url.lastIndex].toString() == ":"){
              //                url = url.dropLast(1)
              //              }
              ""
            } else uri.port.toString()
          }
        } catch (e: java.lang.Exception) {
          e.printStackTrace()
        }

      },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
      placeholder = {Text(text = "http://example.com")},
      singleLine = true,
      isError = ! urlValid
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
      label = {Text(text = StringResources.current.port, style = TextStyle(fontWeight = FontWeight.Bold))},
      value = port,
      onValueChange = {
        port = it
        portValid = (it.isBlank() || checkPort(it))
        // add port to url if not empty
        url = if (portValid && it.isNotEmpty()) {
          if (url.contains(Regex(":\\d+")).not()) {

            val fixedUrl = try {
              val uri = URI(url)
              if (uri.path.isNullOrBlank()) {
                "$url:$it"
              } else {
                val p = uri.path
                val pathIndex = url.indexOf(p)
                val urlWithoutPath = url.substring(0, pathIndex)
                val urlWithPort = "$urlWithoutPath:$it"
                val urlWithPortAndPath = "$urlWithPort$p"
                urlWithPortAndPath
              }

            } catch (e: Exception) {
              e.printStackTrace()
              url
            }
            fixedUrl
          } else {
            url.replace(Regex(":\\d+"), ":$it")
          }
        } else {
          url.replace(Regex(":\\d+"), "")
        }
      },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
      placeholder = {},
      singleLine = true,
      isError = ! portValid
    )
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
      label = {
        Text(
          text = StringResources.current.user,
          style = TextStyle(fontWeight = FontWeight.Bold)
        )
      },
      value = username,
      onValueChange = {username = it},
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
      ),
      placeholder = {Text(text = "")},
      singleLine = true
    )
    Spacer(modifier = Modifier.height(16.dp))

    PasswordInput(
      password = password,
      passwordVisible = passwordVisible,
      editMode = editMode,
      onPasswordChange = {password = it},
      onPasswordVisibleChanged = {
        passwordVisible = it
      }
    )
    Spacer(modifier = Modifier.height(16.dp))

    SelectPathDropdown(resultWebdav,
      initPathList = {_, resultPath ->
        onSelectPath?.invoke(resultWebdav as WebDav, resultPath) as Result<List<NetworkFile>>
      }
    ) {_, resultPath ->
      path = resultPath
      onSelectPath?.invoke(resultWebdav as WebDav, resultPath) as Result<List<NetworkFile>>
    }
    Spacer(modifier = Modifier.height(16.dp))
    //
    //    OutlinedTextField(
    //      label = {
    //        Text(
    //          text = stringResource(R.string.path),
    //          style = TextStyle(fontWeight = FontWeight.Bold)
    //        )
    //      },
    //      value = path,
    //      onValueChange = {
    //        path = it
    //        pathValid = context.checkPath(it)
    //      },
    //      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
    //      placeholder = { HintText(text = "/") },
    //      singleLine = true,
    //      isError = ! pathValid
    //    )
    //    Spacer(modifier = Modifier.height(16.dp))


    fun checkAndFix(): Boolean {
      val realPort = port.ifBlank {WEBDAV.defaultPort.toString()}
      nameValid = checkName(name, true) {
        urlValid = checkUrl(url, true) {
          portValid = checkPort(realPort, true) {
            pathValid = checkPath(path, true)
          }
        }
      }
      return nameValid && urlValid && portValid && pathValid
    }

    var checkingState by remember {mutableStateOf(false)}

    Row {
      ElevatedButton(onClick = {
        if (checkAndFix() && ! checkingState) {
          scope.launch {
            checkingState = true
            val webDav = WebDav(
              url = url,
              user = username,
              passwd = RConfig.encrypt(password),
              name = name.encodeName(),
              path = path
            )
            val result = onTestClick.invoke(webDav)
            result?.onSuccess {
              resultWebdav = webDav
            }
            checkingState = false
          }
        }
      }, modifier = Modifier.padding(10.dp)) {
        if (checkingState) {
          Box {
            CircularProgressIndicator(
              modifier = Modifier
                .padding(5.dp)
                .align(Alignment.Center)
                .size(Dimen.spaceL),
              strokeWidth = 2.dp
            )
          }
        }
        Text(text = StringResources.current.test_connection)
      }

      ElevatedButton(onClick = {
        scope.launch {
          if (checkAndFix()) {
            val webDav = WebDav(
              url = url,
              user = username,
              passwd = RConfig.encrypt(password),
              name = name.encodeName(),
              path = path
            )
            onSaveClick.invoke(webDav)
          }
        }
      }, modifier = Modifier.padding(10.dp)) {
        Text(text = StringResources.current.save, maxLines = 1)
      }
    }

  }

  if (! editMode) {
    LaunchedEffect(Unit) {
      focusRequester.requestFocus()
    }
  }
}

@Preview
@Composable
fun PreviewWebdavConfig() {
  WebdavConfigPage(onTestClick = {null}, onSaveClick = {})
}