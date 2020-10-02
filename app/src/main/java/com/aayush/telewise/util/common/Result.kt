package com.aayush.telewise.util.common

import com.aayush.telewise.api.model.TmdbFailure
import com.haroldadmin.cnradapter.NetworkResponse

sealed class Result<out V, out E> {
    data class Value<V>(val value: V) : Result<V, Nothing>()
    data class Error<E>(val error: E) : Result<Nothing, E>()
}

inline fun <reified V> value(value: V): Result<V, Nothing> = Result.Value(value)
inline fun <reified E> error(error: E): Result<Nothing, E> = Result.Error(error)

/**
 * Map the input [Result] to mapped output only if [this] is [Result.Value]
 *
 * @param I input [Result] value
 * @param O output [Result] value (after mapping)
 * @param E error type of [Result]
 * @param mapper mapping function
 * @return [Result] with mapped value if [Result.Value], otherwise returns [this]
 */
inline fun <reified I, reified O, reified E> Result<I, E>.map(mapper: (I) -> O): Result<O, E> =
    when (this) {
        is Result.Error -> this
        is Result.Value -> value(mapper(value))
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
        is NetworkResponse.Success -> value(body)
        is NetworkResponse.ServerError -> error(body ?: Throwable("Server error"))
        is NetworkResponse.NetworkError -> error(error)
        is NetworkResponse.UnknownError -> error(error)
    }
