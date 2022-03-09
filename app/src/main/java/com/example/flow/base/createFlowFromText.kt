package com.example.flow.base

import com.example.flow.data.features.movie.models.MovieType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

@ExperimentalCoroutinesApi
fun String.createFlowFromText(): Flow<String> {
    Timber.e("text flow start is ${this@createFlowFromText}")
    return callbackFlow {
        trySendBlocking(this@createFlowFromText)
            .onFailure { error -> Timber.e("on failure $error") }
        awaitClose {
            channel.close()
            Timber.e("Await close")
        }
    }
}

@ExperimentalCoroutinesApi
fun MovieType.createFlow(): Flow<MovieType> {
    Timber.e("type flow start is ${this@createFlow}")
    return callbackFlow {
        trySendBlocking(this@createFlow)
            .onFailure { error -> Timber.e("on failure $error") }
        awaitClose {
            channel.close()
            Timber.e("Await close")
        }
    }
}