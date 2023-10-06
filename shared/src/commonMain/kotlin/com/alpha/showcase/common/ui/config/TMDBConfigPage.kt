package com.alpha.showcase.common.ui.config

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alpha.networkfile.storage.external.TMDBSource
import com.alpha.showcase.common.repo.tmdb.TMDBSourceType
import com.alpha.showcase.common.repo.tmdb.data.ImageType
import com.alpha.showcase.common.repo.tmdb.data.Language
import com.alpha.showcase.common.repo.tmdb.data.Region
import com.alpha.showcase.common.ui.StringResources
import com.alpha.showcase.common.ui.theme.Dimen
import com.alpha.showcase.common.utils.ToastUtil
import com.alpha.showcase.common.widget.LargeDropdownMenu
import decodeName
import encodeName
import kotlinx.coroutines.launch

@Composable
fun TMDBConfigPage(
    tmdbSource: TMDBSource? = null,
    onTestClick: suspend (TMDBSource) -> Result<Any>?,
    onSaveClick: suspend (TMDBSource) -> Unit
) {

    var checkingState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var selectedTypeIndex by remember {
        mutableIntStateOf(tmdbSource?.contentType?.let {
            movieTypes.indexOfFirst { type -> type.type == it }
        } ?: 0)
    }
    var selectedLanguageIndex by remember {
        mutableIntStateOf(tmdbSource?.language?.let {
            languages.indexOfFirst { type -> type.value == it }
        } ?: 0)
    }
    var selectedRegionIndex by remember {
        mutableIntStateOf(tmdbSource?.region?.let {
            regions.indexOfFirst { type -> type.value == it }
        } ?: 0)
    }
    var selectedImageTypeIndex by remember {
        mutableIntStateOf(tmdbSource?.imageType?.let {
            imageTypes.indexOfFirst { type -> type.value == it }
        } ?: 0)
    }

    var name by rememberSaveable(key = "name") {
        mutableStateOf(tmdbSource?.name?.decodeName() ?: "")
    }

    val movieType by remember {
        derivedStateOf {
            movieTypes[selectedTypeIndex]
        }
    }
    val language by remember {
        derivedStateOf {
            languages[selectedLanguageIndex]
        }
    }
    val region by remember {
        derivedStateOf {
            regions[selectedRegionIndex]
        }
    }

    val imageType by remember {
        derivedStateOf {
            imageTypes[selectedImageTypeIndex]
        }
    }

    val editMode = tmdbSource != null
    val focusRequester = remember { FocusRequester() }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                label = { Text(StringResources.current.name_require_hint) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )
            Spacer(modifier = Modifier.height(16.dp))

            LargeDropdownMenu(
                label = StringResources.current.choose_type,
                items = movieTypes.map { it.title },
                selectedIndex = selectedTypeIndex,
                onItemSelected = { index, _ -> selectedTypeIndex = index }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LargeDropdownMenu(
                label = StringResources.current.choose_language,
                items = languages.map { it.res },
                selectedIndex = selectedLanguageIndex,
                onItemSelected = { index, _ -> selectedLanguageIndex = index }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = StringResources.current.language_of_film_content,
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            LargeDropdownMenu(
                label = StringResources.current.choose_region,
                items = regions.map { it.res },
                selectedIndex = selectedRegionIndex,
                onItemSelected = { index, _ -> selectedRegionIndex = index }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = StringResources.current.show_movie_from_selected_region,
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            LargeDropdownMenu(
                label = StringResources.current.choose_image_type,
                items = imageTypes.map { it.res },
                selectedIndex = selectedImageTypeIndex,
                onItemSelected = { index, _ -> selectedImageTypeIndex = index }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }



        Row {
            ElevatedButton(onClick = {
                if (!checkingState) {
                    scope.launch {

                        if (name.isEmpty()) {
                            ToastUtil.error(StringResources.current.name_is_invalid)
                        } else {
                            checkingState = true
                            onTestClick.invoke(
                                TMDBSource(
                                    name.encodeName(),
                                    movieType.type,
                                    language.value,
                                    region.value,
                                    imageType.value
                                )
                            )
                            checkingState = false
                        }
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
                if (name.isEmpty()) {
                    ToastUtil.error(StringResources.current.name_is_invalid)
                } else {
                    scope.launch {
                        onSaveClick.invoke(
                            TMDBSource(
                                name.encodeName(),
                                movieType.type,
                                language.value,
                                region.value,
                                imageType.value
                            )
                        )
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


val movieTypes = listOf(
    TMDBSourceType.TopRated,
    TMDBSourceType.Popular,
    TMDBSourceType.Upcoming,
    TMDBSourceType.NowPlaying
)

val languages = Language.values()

val regions = Region.values()

val imageTypes = ImageType.values()
