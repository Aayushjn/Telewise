package com.aayush.telewise.util.common

import com.aayush.telewise.R
import kotlinx.serialization.json.Json

const val BASE_URL = "https://api.themoviedb.org/3/"
const val IMAGE_URL_W500 = "https://image.tmdb.org/t/p/w500"
const val IMAGE_URL_ORIGINAL = "https://image.tmdb.org/t/p/original"
const val TMDB_MOVIE_PREFIX = "https://www.themoviedb.org/movie/"
const val FB_PREFIX = "https://www.facebook.com/"
const val IMDB_MOVIE_PREFIX = "https://www.imdb.com/title/"
const val INSTA_PREFIX = "https://www.instagram.com/"
const val TWITTER_PREFIX = "https://www.twitter.com/"

const val CACHE_SIZE: Long = 5 * 1024 * 1024
const val PAGE_SIZE = 10
const val RETRY_COUNT: Long = 3
const val IMAGE_CORNER_SIZE = 5f
const val VEILED_ITEM_COUNT = 5
const val TOOLBAR_COLLAPSE_HEIGHT_PX = 150

val ROOT_IDS = setOf(R.id.nav_movies, R.id.nav_tv, R.id.nav_people)

val JSON = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}
