package com.example.flow.screens.search_movie.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoNetworkMessage(networkAvailable: Boolean) {
    if (!networkAvailable) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = MaterialTheme.colors.secondaryVariant),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Network is not available", fontSize = 22.sp)
        }
    }
}
