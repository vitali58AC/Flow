package com.example.flow.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun NoSideEffect(func: () -> Unit) {
    val currentCall by rememberUpdatedState(func)

    LaunchedEffect(true) {
        currentCall()
    }
}