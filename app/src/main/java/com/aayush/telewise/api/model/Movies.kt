package com.aayush.telewise.api.model

import android.os.Parcel
import com.aayush.telewise.util.android.KParcelable
import com.aayush.telewise.util.android.parcelableCreator
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCollection(
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("poster_path") val posterPath: String?
)

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1") val iso31661: String,
    @SerialName("name") val name: String
)

@Serializable
data class SpokenLanguage(
    @SerialName("iso_639_1") val iso6391: String,
    @SerialName("name") val name: String
)

@Serializable
data class TmdbMovie(
    @SerialName("adult") val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("belongs_to_collection") val belongsToCollection: MovieCollection?,
    @SerialName("budget") val budget: Long,
    @SerialName("genres") val genres: List<Genre>,
    @SerialName("homepage") val homepage: String?,
    @SerialName("id") val id: Int,
    @SerialName("imdb_id") val imdbId: String?,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String?,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompany>,
    @SerialName("production_countries") val productionCountries: List<ProductionCountry>,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("revenue") val revenue: Long,
    @SerialName("runtime") val runtime: Int?,
    @SerialName("spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    @SerialName("status") val status: String,
    @SerialName("tagline") val tagline: String?,
    @SerialName("title") val title: String,
    @SerialName("video") val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

@Serializable
data class ListMovie(
    @SerialName("adult") val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("id") val id: Int,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("title") val title: String,
    @SerialName("video") val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

@Serializable
data class ListMovieResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<ListMovie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class Dates(
    @SerialName("maximum") val maximum: String,
    @SerialName("minimum") val minimum: String
)

@Serializable
data class NowPlayingResponse(
    @SerialName("dates") val dates: Dates,
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<ListMovie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class TmdbMovieCast(
    @SerialName("cast_id") val castId: Int,
    @SerialName("character") val character: String,
    @SerialName("credit_id") val creditId: String,
    @SerialName("gender") val gender: Int?,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("order") val order: Int,
    @SerialName("profile_path") val profilePath: String?
)

@Serializable
data class TmdbMovieCrew(
    @SerialName("credit_id") val creditId: String,
    @SerialName("department") val department: String,
    @SerialName("gender") val gender: Int?,
    @SerialName("id") val id: Int,
    @SerialName("job") val job: String,
    @SerialName("name") val name: String,
    @SerialName("profile_path") val profilePath: String?
)

@Serializable
data class TmdbMovieCredits(
    @SerialName("cast") val cast: List<TmdbMovieCast>,
    @SerialName("crew") val crew: List<TmdbMovieCrew>,
    @SerialName("id") val id: Int
)

@Serializable
data class ExternalIds(
    @SerialName("facebook_id") val facebookId: String? = null,
    @SerialName("id") val id: Int,
    @SerialName("imdb_id") val imdbId: String? = null,
    @SerialName("instagram_id") val instagramId: String? = null,
    @SerialName("twitter_id") val twitterId: String? = null
) : KParcelable {
    private constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(facebookId)
        writeInt(id)
        writeString(imdbId)
        writeString(instagramId)
        writeString(twitterId)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::ExternalIds)
    }
}
