package com.aayush.telewise.util.android.paging.source

import androidx.paging.PagingSource
import com.aayush.telewise.api.model.PopularPerson
import com.aayush.telewise.api.service.PeopleApi
import com.aayush.telewise.util.android.toLoadResult

class PopularPeoplePagingSource(private val peopleApi: PeopleApi) : PagingSource<Int, PopularPerson>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularPerson> {
        val page = params.key ?: 1
        return peopleApi.getPopularPeople(page).toLoadResult(page) { response ->
            response.results
        }
    }
}
