package com.example.flow.screens.search_movie.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TagText(text: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .padding(16.dp)
        )
        Text(text = text)
    }
}