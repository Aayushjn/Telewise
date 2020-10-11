package com.aayush.telewise.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.filter
import androidx.paging.map
import com.aayush.telewise.api.service.PeopleApi
import com.aayush.telewise.model.UiModel
import com.aayush.telewise.util.android.paging.source.PopularPeoplePagingSource
import com.aayush.telewise.util.common.PAGE_SIZE
import kotlinx.coroutines.flow.map

class PeopleRepository(private val peopleApi: PeopleApi) {
    fun getPopularPeople(showExplicit: Boolean) =
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
}
