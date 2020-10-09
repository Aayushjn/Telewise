package com.aayush.telewise.ui.fragment.people

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aayush.telewise.model.UiModel
import com.aayush.telewise.repository.PeopleRepository
import com.aayush.telewise.util.android.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PeopleViewModel @ViewModelInject constructor(
    private val repository: PeopleRepository,
    private val preferences: AppPreferences
) : ViewModel() {
    suspend fun getPopularPeopleFlow(): Flow<PagingData<UiModel.Person>> =
        repository.getPopularPeople(
            preferences.showExplicit.first()
        ).cachedIn(viewModelScope)
}
