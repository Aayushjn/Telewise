package com.aayush.telewise.util.common

import com.aayush.telewise.api.model.ListMovie
import com.aayush.telewise.api.model.PopularPerson
import com.aayush.telewise.api.model.TmdbCast
import com.aayush.telewise.api.model.TmdbCrew
import com.aayush.telewise.api.model.TmdbMovie
import com.aayush.telewise.api.model.TmdbMovieCast
import com.aayush.telewise.api.model.TmdbMovieCrew
import com.aayush.telewise.api.model.TmdbPerson
import com.aayush.telewise.model.UiModel

infix fun UiModel.Companion.of(movie: ListMovie): UiModel.MovieCollectionModel = UiModel.MovieCollectionModel(
    movie.adult,
    movie.posterPath ?: movie.backdropPath,
    movie.id,
    movie.overview,
    movie.voteAverage,
    movie.title
)

infix fun UiModel.Companion.of(movie: TmdbMovie): UiModel.MovieModel = UiModel.MovieModel(
    movie.adult,
    movie.posterPath ?: movie.backdropPath,
    movie.genres.map { genre ->
        genre.name
    },
    movie.id,
    movie.overview,
    movie.voteAverage,
    movie.releaseDate,
    movie.title
)

infix fun UiModel.Companion.of(cast: TmdbMovieCast): UiModel.PersonCollectionModel = UiModel.PersonCollectionModel(
    false,
    cast.id,
    cast.name,
    cast.character,
    cast.profilePath
)

infix fun UiModel.Companion.of(crew: TmdbMovieCrew): UiModel.PersonCollectionModel = UiModel.PersonCollectionModel(
    false,
    crew.id,
    crew.name,
    crew.job,
    crew.profilePath
)

infix fun UiModel.Companion.of(person: PopularPerson): UiModel.PersonCollectionModel = UiModel.PersonCollectionModel(
    person.adult,
    person.id,
    person.name,
    "",
    person.profilePath
)

infix fun UiModel.Companion.of(person: TmdbPerson): UiModel.PersonModel = UiModel.PersonModel(
    person.adult,
    person.biography,
    person.birthday,
    person.deathday,
    person.id,
    person.name,
    person.placeOfBirth,
    person.profilePath
)

infix fun UiModel.Companion.of(cast: TmdbCast): UiModel.PersonCreditsModel = UiModel.PersonCreditsModel(
    cast.id,
    cast.mediaType,
    cast.posterPath ?: cast.backdropPath,
    cast.character,
    cast.title ?: cast.originalTitle ?: cast.name ?: cast.originalName ?: ""
)

infix fun UiModel.Companion.of(crew: TmdbCrew): UiModel.PersonCreditsModel = UiModel.PersonCreditsModel(
    crew.id,
    crew.mediaType,
    crew.posterPath ?: crew.backdropPath,
    crew.job,
    crew.title ?: crew.originalTitle ?: crew.name ?: crew.originalName ?: ""
)
