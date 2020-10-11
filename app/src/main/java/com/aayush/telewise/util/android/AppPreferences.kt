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
import com.aayush.telewise.util.common.DEFAULT_SAVE_DATA
import com.aayush.telewise.util.common.DEFAULT_SHOW_EXPLICIT
import com.aayush.telewise.util.common.DEFAULT_THEME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AppPreferences(context: Context) {
    private val scope = CoroutineScope(Dispatchers.Default)

    val preferenceDataStore = object : PreferenceDataStore() {
        // KEY_THEME is set up here since ListPreference uses String even for Int values
        // value?.toInt() call is safe since the theme values are always Int
        override fun putString(key: String?, value: String?) {
            when (key) {
                KEY_REGION.name -> scope.launch {
                    saveRegion(value ?: DEFAULT_REGION)
                }
                KEY_THEME.name -> putInt(key, value?.toInt() ?: DEFAULT_THEME)
                else -> throw IllegalArgumentException("Unknown key ($key) encountered!")
            }
        }

        override fun putBoolean(key: String?, value: Boolean) {
            when (key) {
                KEY_SHOW_EXPLICIT.name -> scope.launch {
                    saveShowExplicit(value)
                }
                KEY_SAVE_DATA.name -> scope.launch {
                    saveSaveData(value)
                }
                else -> throw IllegalArgumentException("Unknown key ($key) encountered!")
            }
        }

        override fun putInt(key: String?, value: Int) {
            when (key) {
                KEY_THEME.name -> scope.launch {
                    saveTheme(value)
                }
                else -> throw IllegalArgumentException("Unknown key ($key) encountered!")
            }
        }

        // KEY_THEME is set up here since ListPreference uses String even for Int values
        override fun getString(key: String?, defValue: String?): String =
            when (key) {
                KEY_REGION.name -> runBlocking(scope.coroutineContext) {
                    region.first()
                }
                KEY_THEME.name -> getInt(key, DEFAULT_THEME).toString()
                else -> throw IllegalArgumentException("Unknown key ($key) encountered!")
            }

        override fun getBoolean(key: String?, defValue: Boolean): Boolean =
            when (key) {
                KEY_SHOW_EXPLICIT.name -> runBlocking(scope.coroutineContext) {
                    showExplicit.first()
                }
                KEY_SAVE_DATA.name -> runBlocking(scope.coroutineContext) {
                    saveData.first()
                }
                else -> throw IllegalArgumentException("Unknown key ($key) encountered!")
            }

        override fun getInt(key: String?, defValue: Int): Int =
            when (key) {
                KEY_THEME.name -> runBlocking(scope.coroutineContext) {
                    theme.first()
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

    val theme: Flow<Int>
        get() = dataStore.data.map { prefs ->
            prefs[KEY_THEME] ?: DEFAULT_THEME
        }

    val saveData: Flow<Boolean>
        get() = dataStore.data.map { prefs ->
            prefs[KEY_SAVE_DATA] ?: DEFAULT_SAVE_DATA
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

    suspend fun saveTheme(theme: Int) {
        dataStore.edit { prefs ->
            prefs[KEY_THEME] = theme
        }
    }

    suspend fun saveSaveData(saveData: Boolean) {
        dataStore.edit { prefs ->
            prefs[KEY_SAVE_DATA] = saveData
        }
    }

    companion object {
        private val KEY_REGION = preferencesKey<String>("telewise_KEY_REGION")
        private val KEY_SHOW_EXPLICIT = preferencesKey<Boolean>("telewise_KEY_EXPLICIT")
        private val KEY_THEME = preferencesKey<Int>("telewise_KEY_THEME")
        private val KEY_SAVE_DATA = preferencesKey<Boolean>("telewise_KEY_SAVE_DATA")
    }
}
