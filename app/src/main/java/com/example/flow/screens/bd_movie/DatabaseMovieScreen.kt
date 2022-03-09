package com.example.flow.screens.bd_movie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flow.data.features.movie.models.Movie
import com.example.flow.screens.search_movie.views.EmptyCenterMessage
import com.example.flow.screens.search_movie.views.MoviesListItem

@Composable
fun DatabaseMovieScreen(movieList: List<Movie>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = "Movie in local storage",
                fontWeight = FontWeight.Black,
                fontSize = 30.sp
            )
        }
        items(items = movieList) { movie ->
            MoviesListItem(movie = movie)
        }
        item { EmptyCenterMessage(enable = movieList.isNotEmpty()) }
    }
}