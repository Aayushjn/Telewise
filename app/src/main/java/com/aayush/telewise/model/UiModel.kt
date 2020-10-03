package com.aayush.telewise.model

import androidx.paging.LoadState
import com.aayush.telewise.api.model.ListMovie

sealed class UiModel {
    data class MovieCollectionModel(
        val adult: Boolean,
        val posterPath: String?,
        val id: Int,
        val overview: String,
        val rating: Double,
        val title: String
    ) : UiModel() {
        constructor(movie: ListMovie): this(
            movie.adult,
            movie.posterPath ?: movie.backdropPath,
            movie.id,
            movie.overview,
            movie.voteAverage,
            movie.title
        )
    }
}
