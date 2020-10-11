package com.aayush.telewise.ui.fragment.movies.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.aayush.telewise.model.UiModel.MovieModel
import com.aayush.telewise.model.UiModel.PersonCollectionModel
import com.aayush.telewise.repository.MovieRepository
import com.aayush.telewise.util.common.Result
import com.aayush.telewise.util.common.resultFlow
import kotlinx.coroutines.flow.Flow

class MovieDetailsViewModel @ViewModelInject constructor(
    private val repository: MovieRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    fun saveMovie(movie: MovieModel) = savedStateHandle.set(KEY_MOVIE, movie)

    fun saveExternalIds(ids: Map<String, String?>) = savedStateHandle.set(KEY_EXTERNAL_IDS, ids)

    suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieModel, Throwable>> {
        val movie = savedStateHandle.get<MovieModel>(KEY_MOVIE)
        return if (movie == null || movie.id != movieId) {
            repository.getMovieDetails(movieId)
        } else {
            movie.resultFlow()
        }
    }

    suspend fun getMovieExternalIds(movieId: Int): Flow<Result<Map<String, String?>, Throwable>> {
        val ids = savedStateHandle.get<Map<String, String?>>(KEY_EXTERNAL_IDS)
        return if (ids == null || ids["tmdb"] != movieId.toString()) {
            repository.getExternalIds(movieId)
        } else {
            ids.resultFlow()
        }
    }

    suspend fun getMovieCredits(movieId: Int): Flow<Result<Map<String, List<PersonCollectionModel>>, Throwable>> =
        repository.getMovieCredits(movieId)

    companion object {
        private const val KEY_MOVIE = "MovieKey"
        private const val KEY_EXTERNAL_IDS = "ExternalIdsKey"
    }
}
