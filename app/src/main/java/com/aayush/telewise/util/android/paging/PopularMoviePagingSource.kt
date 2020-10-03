package com.aayush.telewise.util.android.paging

import androidx.paging.PagingSource
import com.aayush.telewise.api.model.ListMovie
import com.aayush.telewise.api.service.MovieApi
import com.aayush.telewise.util.android.toLoadResult

class PopularMoviePagingSource(
    private val movieApi: MovieApi,
    private val region: String
) : PagingSource<Int, ListMovie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListMovie> {
        val page = params.key ?: 1
        return movieApi.getPopularMovies(
            page,
            region
        ).toLoadResult(page) { response ->
            response.results
        }
    }
}
