package com.aayush.telewise.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.aayush.telewise.api.service.PeopleApi
import com.aayush.telewise.model.UiModel
import com.aayush.telewise.model.UiModel.PersonCollectionModel
import com.aayush.telewise.model.UiModel.PersonCreditsModel
import com.aayush.telewise.model.UiModel.PersonModel
import com.aayush.telewise.util.android.paging.source.PopularPeoplePagingSource
import com.aayush.telewise.util.common.PAGE_SIZE
import com.aayush.telewise.util.common.Result
import com.aayush.telewise.util.common.flow
import com.aayush.telewise.util.common.map
import com.aayush.telewise.util.common.of
import com.aayush.telewise.util.common.result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PeopleRepository(private val peopleApi: PeopleApi) {
    fun getPopularPeople(showExplicit: Boolean): Flow<PagingData<PersonCollectionModel>> =
        Pager(PagingConfig(PAGE_SIZE)) { PopularPeoplePagingSource(peopleApi) }
            .flow
            .map { pagingData ->
                pagingData
                    // Convert network model to local model
                    .map { person -> UiModel of person }
                    // Also, if required, filter out adult content
                    .run {
                        if (!showExplicit) {
                            filter { person -> !person.adult }
                        } else {
                            this
                        }
                    }
            }

    suspend fun getPersonDetails(personId: Int): Flow<Result<PersonModel, Throwable>> =
        peopleApi.getPersonById(personId).result()
            .map { person -> UiModel of person }
            .flow()

    suspend fun getExternalIds(personId: Int): Flow<Result<Map<String, String?>, Throwable>> =
        peopleApi.getExternalIds(personId).result()
            .map { externalIds ->
                mapOf(
                    "tmdb" to personId.toString(),
                    "facebook" to externalIds.facebookId,
                    "imdb" to externalIds.imdbId,
                    "instagram" to externalIds.instagramId,
                    "twitter" to externalIds.twitterId
                )
            }
            .flow()

    suspend fun getPersonCredits(personId: Int): Flow<Result<Map<String, List<PersonCreditsModel>>, Throwable>> =
        peopleApi.getCombinedCredits(personId).result()
            .map { credits ->
                mapOf(
                    "cast" to credits.cast.map { cast -> UiModel of cast },
                    "crew" to credits.crew.map { crew -> UiModel of crew }
                )
            }
            .flow()
}
