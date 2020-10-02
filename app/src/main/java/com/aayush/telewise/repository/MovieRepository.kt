package com.aayush.telewise.repository

import com.aayush.telewise.api.model.ListMovieResponse
import com.aayush.telewise.api.service.MovieApi
import com.aayush.telewise.util.common.Result
import com.aayush.telewise.util.common.result

class MovieRepository(private val movieApi: MovieApi) {
    suspend fun getPopularMovies(page: Int, region: String): Result<ListMovieResponse, Throwable> =
        movieApi.getPopularMovies(page, region).result()
}
