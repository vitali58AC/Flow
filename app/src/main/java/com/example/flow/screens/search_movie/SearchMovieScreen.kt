package com.example.flow.screens.search_movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.flow.R
import com.example.flow.data.features.movie.models.Movie
import com.example.flow.data.features.movie.models.MovieType
import com.example.flow.screens.ProgressIndicator
import com.example.flow.screens.search_movie.models.BackHandler
import com.example.flow.screens.search_movie.views.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun SearchMovieScreen(
    movieList: List<Movie>,
    progressIndicator: Boolean,
    responseStatus: Boolean,
    isNetworkAvailable: Boolean,
    onChangeData: (String, MovieType) -> Unit,
    //Вызывает побочный эффект
//    onChangeDataFlow: (Flow<String>, Flow<MovieType>) -> Unit,
    onBack: () -> Unit
) {
    val items = stringArrayResource(id = R.array.movie_types)
    //Иначе всегда будет брать первое значение
    var currentItem by rememberSaveable { mutableStateOf(MovieType.NOT_SELECTED) }
    var title by rememberSaveable { mutableStateOf("") }
     //var textFlow = title.createFlowFromText().collectAsState(initial = "")
//    var typeFlow = currentItem.createFlow()
    BackHandler(onBack = onBack)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            NoNetworkMessage(networkAvailable = isNetworkAvailable) }
        item {
            Image(
                painter = painterResource(id = R.drawable.imbd_logo),
                contentDescription = stringResource(R.string.imdb_logo),
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 15.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(R.string.enter_name))
            TextField(
                value = title,
                onValueChange = {
//                    textFlow = it.createFlowFromText()
//                    onChangeDataFlow(textFlow, typeFlow)
                    title = it
                    onChangeData(title, currentItem)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        0.5.dp,
                        MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                label = { Text("Name") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colors.onPrimary,
                    unfocusedIndicatorColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.background
                )
            )
            TagText(text = stringResource(R.string.choose_type))
            DropdownMenuSearchType(
                items = items,
                onMenuItemClick = { resultType ->
                    currentItem = resultType
                    onChangeData(title, currentItem)
//                    typeFlow = currentItem.createFlow()
//                    onChangeDataFlow(textFlow, typeFlow)
                }
            )
        }
        item {
            if (movieList.isEmpty()) {
                Spacer(modifier = Modifier.height(30.dp))
                ProgressIndicator(visibility = progressIndicator)
            }
        }
        items(items = movieList) { movie ->
            MoviesListItem(movie = movie)
        }
        item {
            EmptyCenterMessage(enable = responseStatus)
        }
    }
}


