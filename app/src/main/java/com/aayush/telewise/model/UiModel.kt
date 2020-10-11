package com.aayush.telewise.model

import android.os.Parcel
import com.aayush.telewise.api.model.ListMovie
import com.aayush.telewise.api.model.PopularPerson
import com.aayush.telewise.api.model.TmdbMovie
import com.aayush.telewise.api.model.TmdbMovieCast
import com.aayush.telewise.api.model.TmdbMovieCrew
import com.aayush.telewise.util.android.KParcelable
import com.aayush.telewise.util.android.parcelableCreator
import com.aayush.telewise.util.android.readBool
import com.aayush.telewise.util.android.writeBool

sealed class UiModel {
    data class MovieCollectionModel(
        val adult: Boolean,
        val posterPath: String?,
        val id: Int,
        val overview: String,
        val rating: Double,
        val title: String
    ) : UiModel(), KParcelable {
        private constructor(parcel: Parcel): this(
            parcel.readBool(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readDouble(),
            parcel.readString()!!
        )

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeBool(adult)
            writeString(posterPath)
            writeInt(id)
            writeString(overview)
            writeDouble(rating)
            writeString(title)
        }

        companion object {
            @JvmField val CREATOR = parcelableCreator(::MovieCollectionModel)
        }
    }

    data class MovieModel(
        val adult: Boolean,
        val posterPath: String?,
        val genres: List<String>,
        val id: Int,
        val overview: String?,
        val rating: Double,
        val releaseDate: String,
        val title: String
    ) : UiModel(), KParcelable {
        @Suppress("UNCHECKED_CAST")
        private constructor(parcel: Parcel): this(
            parcel.readBool(),
            parcel.readString(),
            parcel.readArrayList(String::class.java.classLoader) as List<String>,
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString()!!,
            parcel.readString()!!
        )

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeBool(adult)
            writeString(posterPath)
            writeList(genres)
            writeInt(id)
            writeString(overview)
            writeDouble(rating)
            writeString(releaseDate)
            writeString(title)
        }

        companion object {
            @JvmField val CREATOR = parcelableCreator(::MovieModel)
        }
    }

    data class Person(
        val adult: Boolean = false,
        val id: Int,
        val name: String,
        val role: String,   // character for cast and job for crew
        val profilePath: String?
    ) : UiModel(), KParcelable {
        private constructor(parcel: Parcel): this(
            parcel.readBool(),
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()
        )

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeBool(adult)
            writeInt(id)
            writeString(name)
            writeString(role)
            writeString(profilePath)
        }

        companion object {
            @JvmField val CREATOR = parcelableCreator(::Person)
        }
    }

    companion object {
        infix fun of(movie: ListMovie): MovieCollectionModel = MovieCollectionModel(
            movie.adult,
            movie.posterPath ?: movie.backdropPath,
            movie.id,
            movie.overview,
            movie.voteAverage,
            movie.title
        )

        infix fun of(movie: TmdbMovie): MovieModel = MovieModel(
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

        infix fun of(cast: TmdbMovieCast): Person = Person(
            false,
            cast.id,
            cast.name,
            cast.character,
            cast.profilePath
        )

        infix fun of(crew: TmdbMovieCrew): Person = Person(
            false,
            crew.id,
            crew.name,
            crew.job,
            crew.profilePath
        )

        infix fun of(person: PopularPerson): Person = Person(
            person.adult,
            person.id,
            person.name,
            "",
            person.profilePath
        )
    }
}
