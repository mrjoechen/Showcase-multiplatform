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
import com.alpha.networkfile.storage.FTP
import com.alpha.networkfile.storage.TYPE_FTP
import com.alpha.networkfile.storage.getType
import com.alpha.networkfile.storage.remote.Ftp
import com.alpha.networkfile.util.RConfig
import com.alpha.showcase.common.ui.StringResources
import com.alpha.showcase.common.ui.theme.Dimen
import com.alpha.showcase.common.ui.view.HintText
import com.alpha.showcase.common.utils.checkHost
import com.alpha.showcase.common.utils.checkName
import com.alpha.showcase.common.utils.checkPath
import com.alpha.showcase.common.utils.checkPort
import com.alpha.showcase.common.widget.PasswordInput
import com.alpha.showcase.common.widget.SelectPathDropdown
import decodeName
import encodeName
import kotlinx.coroutines.launch


@Composable
fun FtpConfigPage(
    ftp: Ftp? = null,
    onTestClick: suspend (Ftp) -> Result<Any>?,
    onSaveClick: suspend (Ftp) -> Unit,
    onSelectPath: (suspend (Ftp, String) -> Result<Any>?)? = null
) {

    var name by rememberSaveable(key = "name") {
        mutableStateOf(ftp?.name?.decodeName() ?: "")
    }
    var host by rememberSaveable(key = "host") {
        mutableStateOf(ftp?.host ?: "")
    }
    var port by rememberSaveable(key = "port") {
        mutableStateOf(ftp?.port?.toString() ?: "")
    }
    var username by rememberSaveable(key = "username") {
        mutableStateOf(ftp?.user ?: "")
    }
    var password by rememberSaveable(key = "password") {
        mutableStateOf(ftp?.passwd?.let { RConfig.decrypt(it) } ?: "")
    }
    var path by rememberSaveable(key = "path") {
        mutableStateOf(ftp?.path ?: "")
    }

    val editMode = ftp != null

    var passwordVisible by rememberSaveable(key = "passwordVisible") { mutableStateOf(false) }
    var nameValid by rememberSaveable(key = "nameValid") { mutableStateOf(true) }
    var hostValid by rememberSaveable(key = "hostValid") { mutableStateOf(true) }
    var portValid by rememberSaveable(key = "portValid") { mutableStateOf(true) }
    var pathValid by rememberSaveable(key = "pathValid") { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val label =
        "${StringResources.current.my} ${getType(TYPE_FTP).typeName} ${StringResources.current.source}"
    val focusRequester = remember { FocusRequester() }
    var resultFtp by rememberSaveable {
        mutableStateOf(
            ftp
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.focusRequester(focusRequester),
            label = {
                Text(
                    text = StringResources.current.source_name,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            },
            value = name,
            onValueChange = {
                name = it
                nameValid = checkName(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            placeholder = { HintText(text = label) },
            singleLine = true,
            isError = !nameValid
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            label = {
                Text(
                    text = StringResources.current.host,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            },
            value = host,
            onValueChange = {
                host = it
                hostValid = checkHost(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            placeholder = { HintText("192.168.1.1") },
            singleLine = true,
            isError = !hostValid
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            label = {
                Text(
                    text = StringResources.current.port,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            },
            value = port,
            onValueChange = {
                port = it
                portValid = checkPort(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            placeholder = { HintText(text = FTP.defaultPort.toString()) },
            singleLine = true,
            isError = !portValid
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
            onValueChange = { username = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            placeholder = { Text(text = "") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        PasswordInput(
            password = password,
            passwordVisible = passwordVisible,
            editMode = editMode,
            onPasswordChange = { password = it },
            onPasswordVisibleChanged = {
                passwordVisible = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        SelectPathDropdown(resultFtp,
            initPathList = { _, resultPath ->
                onSelectPath?.invoke(
                    resultFtp as Ftp,
                    resultPath
                ) as Result<List<NetworkFile>>
            }) { _, resultPath ->
            path = resultPath
            onSelectPath?.invoke(resultFtp!!, resultPath) as Result<List<NetworkFile>>
        }

//        OutlinedTextField(
//            label = {
//                Text(
//                    text = stringResource(R.string.path),
//                    style = TextStyle(fontWeight = FontWeight.Bold)
//                )
//            },
//            value = path,
//            onValueChange = {
//                path = it
//                pathValid = context.checkPath(it)
//            },
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Done
//            ),
//            placeholder = { HintText(text = "/") },
//            singleLine = true,
//            isError = !pathValid
//        )
        Spacer(modifier = Modifier.height(16.dp))

        fun checkAndFix(): Boolean {
            val realPort = port.ifBlank { FTP.defaultPort.toString() }
            nameValid = checkName(name, true) {
                hostValid = checkHost(host, true) {
                    portValid = checkPort(realPort, true) {
                        pathValid = checkPath(path, true)
                    }
                }
            }
            return nameValid && hostValid && portValid && pathValid
        }

        var checkingState by remember { mutableStateOf(false) }

        Row {
            ElevatedButton(onClick = {
                if (checkAndFix() && !checkingState) {
                    scope.launch {
                        checkingState = true
                        val ftp = Ftp(
                            host = host,
                            port = port.ifBlank { FTP.defaultPort.toString() }.toInt(),
                            user = username,
                            passwd = RConfig.encrypt(password),
                            name = name.encodeName(),
                            path = path
                        )
                        val result = onTestClick.invoke(ftp)
                        result?.onSuccess {
                            resultFtp = ftp
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
                        val ftp = Ftp(
                            host = host,
                            port = port.ifBlank { FTP.defaultPort.toString() }.toInt(),
                            user = username,
                            passwd = RConfig.encrypt(password),
                            name = name.encodeName(),
                            path = path
                        )
                        onSaveClick.invoke(ftp)
                    }
                }
            }, modifier = Modifier.padding(10.dp)) {
                Text(text = StringResources.current.save, maxLines = 1)
            }
        }
    }
    if (!editMode) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}


@Preview
@Composable
fun PreviewFtpConfig() {
    FtpConfigPage(onTestClick = { null }, onSaveClick = {})
}