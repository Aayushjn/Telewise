package com.aayush.telewise.util.android

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.preference.PreferenceDataStore
import com.aayush.telewise.util.common.DATA_STORE_FILE
import com.aayush.telewise.util.common.DEFAULT_REGION
import com.aayush.telewise.util.common.DEFAULT_SHOW_EXPLICIT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AppPreferences(context: Context) {
    val preferenceDataStore = object : PreferenceDataStore() {
        override fun putString(key: String?, value: String?) {
            when (key) {
                KEY_REGION.name -> CoroutineScope(Dispatchers.Default).launch {
                    saveRegion(value ?: DEFAULT_REGION)
                }
                else -> throw IllegalArgumentException("Unknown key ($key) encountered!")
            }
        }

        override fun putBoolean(key: String?, value: Boolean) {
            when (key) {
                KEY_SHOW_EXPLICIT.name -> CoroutineScope(Dispatchers.Default).launch {
                    saveShowExplicit(value)
                }
                else -> throw IllegalArgumentException("Unknown key ($key) encountered!")
            }
        }

        override fun getString(key: String?, defValue: String?): String =
            when (key) {
                KEY_REGION.name -> runBlocking(Dispatchers.Default) {
                    region.first()
                }
                else -> throw IllegalArgumentException("Unknown key ($key) encountered!")
            }

        override fun getBoolean(key: String?, defValue: Boolean): Boolean =
            when (key) {
                KEY_SHOW_EXPLICIT.name -> runBlocking(Dispatchers.Default) {
                    showExplicit.first()
                }
                else -> throw IllegalArgumentException("Unknown key ($key) encountered!")
            }
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(DATA_STORE_FILE)

    val region: Flow<String>
        get() = dataStore.data.map { prefs ->
            prefs[KEY_REGION] ?: DEFAULT_REGION
        }

    val showExplicit: Flow<Boolean>
        get() = dataStore.data.map { prefs ->
            prefs[KEY_SHOW_EXPLICIT] ?: DEFAULT_SHOW_EXPLICIT
        }

    suspend fun saveRegion(region: String) {
        dataStore.edit { prefs ->
            prefs[KEY_REGION] = region
        }
    }

    suspend fun saveShowExplicit(showExplicit: Boolean) {
        dataStore.edit { prefs ->
            prefs[KEY_SHOW_EXPLICIT] = showExplicit
        }
    }

    companion object {
        private val KEY_REGION = preferencesKey<String>("telewise_KEY_REGION")
        private val KEY_SHOW_EXPLICIT = preferencesKey<Boolean>("telewise_KEY_EXPLICIT")
    }
}
