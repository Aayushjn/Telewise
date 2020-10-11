package com.aayush.telewise.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.aayush.telewise.api.service.MovieApi
import com.aayush.telewise.model.UiModel
import com.aayush.telewise.model.UiModel.MovieModel
import com.aayush.telewise.model.UiModel.PersonCollectionModel
import com.aayush.telewise.util.android.paging.source.PopularMoviePagingSource
import com.aayush.telewise.util.common.PAGE_SIZE
import com.aayush.telewise.util.common.Result
import com.aayush.telewise.util.common.flow
import com.aayush.telewise.util.common.map
import com.aayush.telewise.util.common.of
import com.aayush.telewise.util.common.result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(private val movieApi: MovieApi) {
    fun getPopularMovies(region: String, showExplicit: Boolean): Flow<PagingData<UiModel.MovieCollectionModel>> =
        Pager(PagingConfig(PAGE_SIZE)) { PopularMoviePagingSource(movieApi, region) }
            .flow
            .map { pagingData ->
                pagingData
                    // Convert network model to local model
                    .map { movie -> UiModel of movie }
                    // Also, if required, filter out adult content
                    .run {
                        if (!showExplicit) {
                            filter { movie -> !movie.adult }
                        } else {
                            this
                        }
                    }
            }

    suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieModel, Throwable>> =
        movieApi.getMovieById(movieId).result()
            .map { movie -> UiModel of movie }
            .flow()

    suspend fun getExternalIds(movieId: Int): Flow<Result<Map<String, String?>, Throwable>> =
        movieApi.getMovieExternalIds(movieId).result()
            .map { externalIds ->
                mapOf(
                    "tmdb" to movieId.toString(),
                    "facebook" to externalIds.facebookId,
                    "imdb" to externalIds.imdbId,
                    "instagram" to externalIds.instagramId,
                    "twitter" to externalIds.twitterId
                )
            }
            .flow()

    suspend fun getMovieCredits(movieId: Int): Flow<Result<Map<String, List<PersonCollectionModel>>, Throwable>> =
        movieApi.getMovieCredits(movieId).result()
            .map { credits ->
                mapOf(
                    "cast" to credits.cast.map { cast -> UiModel of cast },
                    "crew" to credits.crew.map { crew -> UiModel of crew }
                )
            }
            .flow()
}
