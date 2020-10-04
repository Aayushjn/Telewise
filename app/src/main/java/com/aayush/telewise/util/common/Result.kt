package com.aayush.telewise.util.common

import com.aayush.telewise.api.model.TmdbFailure
import com.haroldadmin.cnradapter.NetworkResponse

sealed class Result<out V, out E> {
    data class Value<V>(val value: V) : Result<V, Nothing>()
    data class Error<E>(val error: E) : Result<Nothing, E>()
}

/**
 * Map the input [Result] to mapped output only if [this] is [Result.Value]
 *
 * @param I input [Result] value
 * @param O output [Result] value (after mapping)
 * @param mapper mapping function
 * @return [Result] with mapped value if [Result.Value], otherwise returns [this]
 */
inline fun <reified I, reified O> Result<I, Throwable>.map(mapper: (I) -> O): Result<O, Throwable> =
    when (this) {
        is Result.Value -> Result.Value(mapper(value))
        is Result.Error -> this
    }

inline fun <reified T> Result<T, Throwable>.onSuccess(block: Result.Value<T>.() -> Unit): Result<T, Throwable> {
    if (this is Result.Value) block()
    return this
}

inline fun <reified T> Result<T, Throwable>.onError(block: Result.Error<Throwable>.() -> Unit): Result<T, Throwable> {
    if (this is Result.Error) block()
    return this
}

/**
 * Convert a [NetworkResponse] object to [Result] based on response type
 * Useful to prevent API return values being propagated beyond repository
 *
 * @param T type of success value returned by API
 * @return [Result] wrapping output of [NetworkResponse] based on response type
 */
inline fun <reified T : Any> NetworkResponse<T, TmdbFailure>.result(): Result<T, Throwable> =
    when (this) {
        is NetworkResponse.Success -> Result.Value(body)
        is NetworkResponse.ServerError -> Result.Error(body ?: Throwable("Server error"))
        is NetworkResponse.NetworkError -> Result.Error(error)
        is NetworkResponse.UnknownError -> Result.Error(error)
    }
