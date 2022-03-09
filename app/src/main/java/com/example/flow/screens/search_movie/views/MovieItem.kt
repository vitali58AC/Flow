package com.example.flow.screens.search_movie.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flow.data.features.movie.models.Movie
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MoviesListItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(8.dp))
    ) {
        Row {
            Column {
                GlideImage(
                    imageModel = movie.poster,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(160.dp)
                        .width(120.dp),
                    alignment = Alignment.CenterStart
                )
                Column(
                    modifier = Modifier
                        .width(120.dp)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = movie.year ?: "",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = movie.title ?: "",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = movie.imdbID ?: "",
                    style = MaterialTheme.typography.caption,
                    fontSize = 8.sp
                )
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize(),
                    elevation = 2.dp,
                    shape = RoundedCornerShape(corner = CornerSize(8.dp))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .background(Color.LightGray),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Text(
                            text = movie.type ?: "",
                            style = MaterialTheme.typography.caption,
                        )
                    }
                }
            }
        }
    }
}