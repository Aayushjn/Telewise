package com.aayush.telewise.model

import android.os.Parcel
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

    data class PersonCollectionModel(
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
            @JvmField val CREATOR = parcelableCreator(::PersonCollectionModel)
        }
    }

    data class PersonModel(
        val adult: Boolean,
        val biography: String,
        val birthday: String?,
        val deathday: String?,
        val id: Int,
        val name: String,
        val placeOfBirth: String?,
        val profilePath: String?
    ) : UiModel(), KParcelable {
        private constructor(parcel: Parcel): this(
            parcel.readBool(),
            parcel.readString()!!,
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString(),
            parcel.readString()
        )

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeBool(adult)
            writeString(biography)
            writeString(birthday)
            writeString(deathday)
            writeInt(id)
            writeString(name)
            writeString(placeOfBirth)
            writeString(profilePath)
        }

        companion object {
            @JvmField val CREATOR = parcelableCreator(::PersonModel)
        }
    }

    data class PersonCreditsModel(
        val id: Int,
        val mediaType: String,
        val profilePath: String?,
        val role: String,
        val title: String,
    ) : UiModel(), KParcelable {
        private constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString(),
            parcel.readString()!!,
            parcel.readString()!!
        )

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeInt(id)
            writeString(mediaType)
            writeString(profilePath)
            writeString(role)
            writeString(title)
        }

        companion object {
            @JvmField val CREATOR = parcelableCreator(::PersonCreditsModel)
        }
    }

    // Required to define infix extensions
    companion object
}
