package com.aayush.telewise.ui.fragment.people.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.aayush.telewise.model.UiModel.PersonCreditsModel
import com.aayush.telewise.model.UiModel.PersonModel
import com.aayush.telewise.repository.PeopleRepository
import com.aayush.telewise.util.common.Result
import com.aayush.telewise.util.common.resultFlow
import kotlinx.coroutines.flow.Flow

class PersonDetailsViewModel @ViewModelInject constructor(
    private val repository: PeopleRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    fun savePerson(person: PersonModel) = savedStateHandle.set(KEY_PERSON, person)

    fun saveExternalIds(ids: Map<String, String?>) = savedStateHandle.set(KEY_EXTERNAL_IDS, ids)

    suspend fun getPersonDetails(personId: Int): Flow<Result<PersonModel, Throwable>> {
        val person = savedStateHandle.get<PersonModel>(KEY_PERSON)
        return if (person == null || person.id != personId) {
            repository.getPersonDetails(personId)
        } else {
            person.resultFlow()
        }
    }

    suspend fun getExternalIds(personId: Int): Flow<Result<Map<String, String?>, Throwable>> {
        val ids = savedStateHandle.get<Map<String, String?>>(KEY_EXTERNAL_IDS)
        return if (ids == null || ids["tmdb"] != personId.toString()) {
            repository.getExternalIds(personId)
        } else {
            ids.resultFlow()
        }
    }

    suspend fun getCredits(personId: Int): Flow<Result<Map<String, List<PersonCreditsModel>>, Throwable>> =
        repository.getPersonCredits(personId)

    companion object {
        private const val KEY_PERSON = "PersonKey"
        private const val KEY_EXTERNAL_IDS = "ExternalIdsKey"
    }
}
