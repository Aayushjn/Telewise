package com.aayush.telewise.util.common

import kotlinx.serialization.json.Json

const val BASE_URL = "https://api.themoviedb.org/3/"
const val IMAGE_URL_W500 = "https://image.tmdb.org/t/p/w500"
const val IMAGE_URL_ORIGINAL = "https://image.tmdb.org/t/p/original"

const val CACHE_SIZE: Long = 5 * 1024 * 1024
const val PAGE_SIZE = 10
const val RETRY_COUNT: Long = 3
const val IMAGE_CORNER_SIZE = 5f

val JSON = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}
