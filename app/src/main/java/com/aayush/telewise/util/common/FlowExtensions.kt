package com.aayush.telewise.util.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Produces a [Flow] consisting of [Result] objects to represent state
 *
 * @param T type of success value
 * @return flow of [Result] wrapped values
 */
inline fun <reified T> T.resultFlow(): Flow<Result<T, Throwable>> = flow {
    when (this) {
        is Throwable -> emit(Result.Error(this@resultFlow as Throwable))
        else -> emit(Result.Value(this@resultFlow))
    }
}

/**
 * Produces a [Flow] of any generic type
 *
 * @param T type of value to emit
 * @return flow of [this]
 */
inline fun <reified T> T.flow(): Flow<T> = flow { emit(this@flow) }
