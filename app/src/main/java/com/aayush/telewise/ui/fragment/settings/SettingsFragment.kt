package com.aayush.telewise.ui.fragment.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import com.aayush.telewise.R
import com.aayush.telewise.databinding.FragmentSettingsBinding
import com.aayush.telewise.util.android.AppPreferences
import com.aayush.telewise.util.android.viewBinding
import com.aayush.telewise.util.common.ROOT_IDS
import dagger.hilt.android.AndroidEntryPoint
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

        preferenceManager.findPreference<CheckBoxPreference>("telewise_KEY_EXPLICIT")
            ?.also { pref ->
                pref.setOnPreferenceChangeListener { _, newValue ->
                    lifecycleScope.launch {
                        appPreferences.saveShowExplicit(newValue as Boolean)
                    }
                    true
                }
            }
    }
}
