package com.example.flow.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import timber.log.Timber

fun <T> Flow<T>.log(message: String): Flow<T> = transform { value ->
    Timber.e(message)
    emit(value)
}
