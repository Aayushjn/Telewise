package com.aayush.telewise.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TmdbFailure(
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String
) : Exception(toString()) {
    override fun toString(): String = "Code=$statusCode, Message=$statusMessage"
}

@Serializable
data class Genre(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)

@Serializable
data class ProductionCompany(
    @SerialName("id") val id: Int,
    @SerialName("logo_path") val logoPath: String?,
    @SerialName("name") val name: String,
    @SerialName("origin_country") val originCountry: String
)

@Serializable
data class Review(
    @SerialName("author") val author: String,
    @SerialName("content") val content: String,
    @SerialName("id") val id: Int,
    @SerialName("url") val url: String
)

@Serializable
data class ReviewResponse(
    @SerialName("id") val id: Int,
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<Review>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
