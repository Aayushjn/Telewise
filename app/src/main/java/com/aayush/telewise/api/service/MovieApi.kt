package com.aayush.telewise.api.service

import com.aayush.telewise.api.model.ExternalIds
import com.aayush.telewise.api.model.ListMovieResponse
import com.aayush.telewise.api.model.NowPlayingResponse
import com.aayush.telewise.api.model.ReviewResponse
import com.aayush.telewise.api.model.TmdbFailure
import com.aayush.telewise.api.model.TmdbMovie
import com.aayush.telewise.api.model.TmdbMovieCredits
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("region") region: String
    ): NetworkResponse<ListMovieResponse, TmdbFailure>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("region") region: String
    ): NetworkResponse<NowPlayingResponse, TmdbFailure>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("region") region: String
    ): NetworkResponse<NowPlayingResponse, TmdbFailure>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<TmdbMovie, TmdbFailure>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<TmdbMovieCredits, TmdbFailure>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): NetworkResponse<ReviewResponse, TmdbFailure>

    @GET("movie/{movie_id}/external_ids")
    suspend fun getMovieExternalIds(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<ExternalIds, TmdbFailure>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): NetworkResponse<ListMovieResponse, TmdbFailure>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): NetworkResponse<ListMovieResponse, TmdbFailure>
}
