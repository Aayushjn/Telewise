package com.aayush.telewise.api.service

import com.aayush.telewise.api.model.CombinedCreditsResponse
import com.aayush.telewise.api.model.ExternalIds
import com.aayush.telewise.api.model.PopularPeopleResponse
import com.aayush.telewise.api.model.TmdbFailure
import com.aayush.telewise.api.model.TmdbPerson
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {
    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("page") page: Int
    ): NetworkResponse<PopularPeopleResponse, TmdbFailure>

    @GET("person/{person_id}")
    suspend fun getPersonById(
        @Path("person_id") personId: Int
    ): NetworkResponse<TmdbPerson, TmdbFailure>

    @GET("person/{person_id}/combined_credits")
    suspend fun getCombinedCredits(
        @Path("person_id") personId: Int
    ): NetworkResponse<CombinedCreditsResponse, TmdbFailure>

    @GET("person/{person_id}/external_ids")
    suspend fun getExternalIds(
        @Path("person_id") personId: Int
    ): NetworkResponse<ExternalIds, TmdbFailure>
}
