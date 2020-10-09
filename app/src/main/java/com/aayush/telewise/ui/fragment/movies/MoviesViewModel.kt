package com.aayush.telewise.ui.fragment.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aayush.telewise.model.UiModel
import com.aayush.telewise.repository.MovieRepository
import com.aayush.telewise.util.android.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class MoviesViewModel @ViewModelInject constructor(
    private val repository: MovieRepository,
    private val preferences: AppPreferences
) : ViewModel() {
    suspend fun getPopularMoviesFlow(): Flow<PagingData<UiModel.MovieCollectionModel>> =
        repository.getPopularMovies(
            preferences.region.first(),
            preferences.showExplicit.first()
        ).cachedIn(viewModelScope)
}
