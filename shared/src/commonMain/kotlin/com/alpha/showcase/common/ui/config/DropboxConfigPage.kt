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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alpha.X
import com.alpha.networkfile.model.NetworkFile
import com.alpha.networkfile.storage.DROP_BOX
import com.alpha.networkfile.storage.TYPE_DROPBOX
import com.alpha.networkfile.storage.drive.DropBox
import com.alpha.networkfile.storage.getType
import com.alpha.networkfile.storage.remote.OAuthRcloneApi
import com.alpha.showcase.common.ui.StringResources
import com.alpha.showcase.common.ui.theme.Dimen
import com.alpha.showcase.common.ui.view.HintText
import com.alpha.showcase.common.utils.Log
import com.alpha.showcase.common.utils.ToastUtil
import com.alpha.showcase.common.utils.checkName
import com.alpha.showcase.common.utils.checkPath
import com.alpha.showcase.common.widget.SelectPathDropdown
import decodeName
import encodeName
import kotlinx.coroutines.launch

@Composable
fun DropboxConfigPage(
    dropbox: DropBox? = null,
    onTestClick: suspend (DropBox) -> Result<Any>?,
    onSaveClick: suspend (DropBox) -> Unit,
    onLinkClick: suspend (OAuthRcloneApi) -> OAuthRcloneApi?,
    onSelectPath: (suspend (DropBox, String) -> Result<Any>?)? = null
) {

    var name by rememberSaveable(key = "name") {
        mutableStateOf(dropbox?.name?.decodeName() ?: "")
    }

    var path by rememberSaveable(key = "path") {
        mutableStateOf(dropbox?.path ?: "")
    }

    var nameValid by rememberSaveable(key = "nameValid") { mutableStateOf(true) }
    var pathValid by rememberSaveable(key = "pathValid") { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val label =
        "${getType(TYPE_DROPBOX).typeName} ${StringResources.current.source}"
    val focusRequester = remember { FocusRequester() }

    var resultDropbox by remember {
        mutableStateOf(
            dropbox
        )
    }

    val editMode = dropbox != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            modifier = Modifier
                .padding(Dimen.spaceM),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource("ic_dropbox.png"),
                contentDescription = DROP_BOX.typeName,
                tint = Color.Unspecified
            )
            Text(
                text = DROP_BOX.typeName,
                style = MaterialTheme.typography.bodySmall.merge(),
                modifier = Modifier.padding(Dimen.spaceM),
                textAlign = TextAlign.Center
            )
        }

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


        SelectPathDropdown(resultDropbox,
            initPathList = { _, resultPath ->
                onSelectPath?.invoke(
                    resultDropbox as DropBox,
                    resultPath
                ) as Result<List<NetworkFile>>
            }) { _, resultPath ->
            path = resultPath
            onSelectPath?.invoke(resultDropbox!!, resultPath) as Result<List<NetworkFile>>
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
            nameValid = checkName(name, true) {
                pathValid = checkPath(path, true)
            }
            return nameValid && pathValid
        }

        var checkingState by remember { mutableStateOf(false) }



        ElevatedButton(onClick = {
            scope.launch {
                if (checkAndFix()) {
                    val drive = DropBox(
                        name = name.encodeName(),
                        cid = X.DROPBOX_APP_KEY,
                        sid = X.DROPBOX_APP_SECRET
                    )
                    val oAuthRcloneApi = onLinkClick.invoke(drive)
                    (oAuthRcloneApi as DropBox?)?.apply {
                        resultDropbox = this
                        val result = onTestClick.invoke(this)
                        result?.onSuccess {
                            (it as List<NetworkFile>).apply {
                                Log.d("onSuccess $it")
                            }
                        }
                    }
                }
            }
        }, modifier = Modifier.padding(10.dp)) {
            Text(text = StringResources.current.link_to + " " + DROP_BOX.typeName, maxLines = 1)
        }


        Row {
            ElevatedButton(onClick = {
                if (checkAndFix() && !checkingState) {
                    scope.launch {
                        checkingState = true
                        resultDropbox?.apply {
                            onTestClick.invoke(this)
                        }?: kotlin.run {
                            Log.d("Result Dropbox is null")
                            StringResources.current.error
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
                        resultDropbox?.apply {
                            val dropbox = DropBox(
                                name = name.encodeName(),
                                cid = X.DROPBOX_APP_KEY,
                                sid = X.DROPBOX_APP_SECRET,
                                path = path,
                                token = token
                            )
                            onSaveClick.invoke(dropbox)
                        }?: kotlin.run {
                            Log.d("Result Dropbox is null")
                            ToastUtil.error(StringResources.current.error)
                        }
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
fun PreviewDropboxConfig() {
    DropboxConfigPage(onTestClick = { null }, onSaveClick = {}, onLinkClick = {null})
}