package com.aayush.telewise.util.android.paging

import androidx.paging.PagingSource
import com.aayush.telewise.api.model.ListMovie
import com.aayush.telewise.repository.MovieRepository
import com.aayush.telewise.util.common.Result
import com.aayush.telewise.util.common.map

class PopularMoviePagingSource(
    private val repository: MovieRepository
) : PagingSource<Int, ListMovie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListMovie> {
        val page = params.key ?: 1
        val response = repository.getPopularMovies(
            page,
            "in"
        ).map { movieResponse ->
            movieResponse.results
        }
        return when (response) {
            is Result.Value -> LoadResult.Page(
                response.value,
                if (page == 1) null else page - 1,
                page + 1
            )
            is Result.Error -> LoadResult.Error(response.error)
        }
    }
}
