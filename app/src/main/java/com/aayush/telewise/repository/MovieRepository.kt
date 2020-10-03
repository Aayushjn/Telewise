package com.aayush.telewise.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.aayush.telewise.api.service.MovieApi
import com.aayush.telewise.model.UiModel
import com.aayush.telewise.util.android.paging.PopularMoviePagingSource
import com.aayush.telewise.util.common.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(private val movieApi: MovieApi) {
    fun getPopularMovies(region: String, showExplicit: Boolean): Flow<PagingData<UiModel.MovieCollectionModel>> =
        Pager(PagingConfig(PAGE_SIZE)) { PopularMoviePagingSource(movieApi, region) }
            .flow
            .map { pagingData ->
                pagingData
                    // Convert network model to local model
                    .map { movie ->
                        UiModel.MovieCollectionModel(movie)
                    }.run {
                        // Also, if required, filter out adult content
                        if (!showExplicit) {
                            filter { movie -> !movie.adult }
                        } else {
                            this
                        }
                    }
            }
}
