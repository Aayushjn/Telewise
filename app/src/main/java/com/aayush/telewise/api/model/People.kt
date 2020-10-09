package com.aayush.telewise.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbPerson(
    @SerialName("adult") val adult: Boolean,
    @SerialName("also_known_as") val alsoKnownAs: List<String>,
    @SerialName("biography") val biography: String,
    @SerialName("birthday") val birthday: String?,
    @SerialName("deathday") val deathday: String?,
    @SerialName("gender") val gender: Int,
    @SerialName("homepage") val homepage: String?,
    @SerialName("id") val id: Int,
    @SerialName("imdb_id") val imdbId: String,
    @SerialName("known_for_department") val knownForDepartment: String,
    @SerialName("name") val name: String,
    @SerialName("place_of_birth") val placeOfBirth: String?,
    @SerialName("popularity") val popularity: Double,
    @SerialName("profile_path") val profilePath: String?
)

@Serializable
data class KnownFor(
    // Common for movie and TV
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("id") val id: Int,
    @SerialName("media_type") val mediaType: String,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    // TV only
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("original_name") val originalName: String? = null,
    @SerialName("origin_country") val originCountry: List<String>? = null,
    // Movie only
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("video") val video: Boolean? = null
)

@Serializable
data class PopularPerson(
    @SerialName("adult") val adult: Boolean,
    @SerialName("gender") val gender: Int,
    @SerialName("id") val id: Int,
    @SerialName("known_for") val knownFor: List<KnownFor>,
    @SerialName("known_for_department") val knownForDepartment: String,
    @SerialName("name") val name: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("profile_path") val profilePath: String?
)

@Serializable
data class PopularPeopleResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<PopularPerson>,
    @SerialName("total_results") val totalResults: Int,
    @SerialName("total_pages") val totalPages: Int
)

@Serializable
data class TmdbCast(
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("character") val character: String,
    @SerialName("credit_id") val creditId: String,
    @SerialName("episode_count") val episodeCount: Int? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("id") val id: Int,
    @SerialName("media_type") val mediaType: String,
    @SerialName("name") val name: String? = null,
    @SerialName("origin_country") val originCountry: List<String>? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_name") val originalName: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("video") val video: Boolean? = null,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

@Serializable
data class TmdbCrew(
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("credit_id") val creditId: String,
    @SerialName("department") val department: String,
    @SerialName("episode_count") val episodeCount: Int? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("id") val id: Int,
    @SerialName("job") val job: String,
    @SerialName("media_type") val mediaType: String,
    @SerialName("name") val name: String? = null,
    @SerialName("origin_country") val originCountry: List<String>? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_name") val originalName: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("video") val video: Boolean? = null,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

@Serializable
data class CombinedCreditsResponse(
    @SerialName("cast") val cast: List<TmdbCast>,
    @SerialName("crew") val crew: List<TmdbCrew>,
    @SerialName("id") val id: Int
)
