package com.aayush.telewise.ui.fragment.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.aayush.telewise.R
import com.aayush.telewise.databinding.FragmentSettingsBinding
import com.aayush.telewise.util.android.AppPreferences
import com.aayush.telewise.util.android.viewBinding
import com.aayush.telewise.util.common.ROOT_IDS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    @Inject lateinit var appPreferences: AppPreferences
    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        preferenceManager.preferenceDataStore = appPreferences.preferenceDataStore
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration = AppBarConfiguration(ROOT_IDS)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.layoutToolbar.toolbar)
        binding.layoutToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)

        loadPreferences()

        listenToPreferences()
    }

    private fun loadPreferences() {
        lifecycleScope.launchWhenCreated {
            preferenceManager.findPreference<ListPreference>("telewise_KEY_THEME")!!.value =
                appPreferences.theme.first().toString()

            preferenceManager.findPreference<SwitchPreference>("telewise_KEY_SAVE_DATA")!!.isChecked =
                appPreferences.saveData.first()

            preferenceManager.findPreference<SwitchPreference>("telewise_KEY_EXPLICIT")!!.isChecked =
                appPreferences.showExplicit.first()
        }
    }

    private fun listenToPreferences() {
        preferenceManager.findPreference<ListPreference>("telewise_KEY_THEME")!!
            .also { pref ->
                pref.setOnPreferenceChangeListener { _, newValue ->
                    lifecycleScope.launch {
                        val theme = (newValue as String).toInt()
                        appPreferences.saveTheme(theme)
                        AppCompatDelegate.setDefaultNightMode(theme)
                    }
                    true
                }
            }

        preferenceManager.findPreference<SwitchPreference>("telewise_KEY_SAVE_DATA")!!
            .also { pref ->
                pref.setOnPreferenceChangeListener { _, newValue ->
                    lifecycleScope.launch {
                        appPreferences.saveSaveData(newValue as Boolean)
                    }
                    true
                }
            }

        preferenceManager.findPreference<SwitchPreference>("telewise_KEY_EXPLICIT")!!
            .also { pref ->
                pref.setOnPreferenceChangeListener { _, newValue ->
                    lifecycleScope.launch {
                        appPreferences.saveShowExplicit(newValue as Boolean)
                    }
                    true
                }
            }
    }
}
