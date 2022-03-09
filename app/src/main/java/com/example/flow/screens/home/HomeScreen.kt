package com.example.flow.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    onMovieDatabaseClick: () -> Unit,
    generatorValue: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Logo image
        Button(onClick = { onSearchClick() }) {
            Text(text = "Search movie")
        }
        Button(onClick = { onMovieDatabaseClick() }) {
            Text(text = "Movie in database")
        }
/*        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Generator example value = $generatorValue",
            fontSize = 30.sp,
            fontWeight = FontWeight.Black
        )*/

    }
}