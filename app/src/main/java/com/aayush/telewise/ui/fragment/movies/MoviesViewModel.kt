package com.aayush.telewise.ui.fragment.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.aayush.telewise.repository.MovieRepository
import com.aayush.telewise.util.android.paging.PopularMoviePagingSource
import com.aayush.telewise.util.common.PAGE_SIZE

class MoviesViewModel @ViewModelInject constructor(
    repository: MovieRepository
) : ViewModel() {
    val popularMoviesFlow = Pager(PagingConfig(PAGE_SIZE)) { PopularMoviePagingSource(repository) }
        .flow
        .cachedIn(viewModelScope)
}
