package com.aayush.telewise.ui.fragment.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aayush.telewise.repository.MovieRepository

class MoviesViewModel @ViewModelInject constructor(
    repository: MovieRepository
) : ViewModel() {
    // TODO: Replace hardcoded parameters with SharedPreferences backed data
    val popularMoviesFlow = repository.getPopularMovies("IN", true)
        .cachedIn(viewModelScope)
}
