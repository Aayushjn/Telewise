package com.aayush.telewise

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.aayush.telewise.util.android.AppPreferences
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class TelewiseApp : Application() {
    @Inject lateinit var preferences: AppPreferences

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.Main).launch {
            AppCompatDelegate.setDefaultNightMode(preferences.theme.first())
        }
    }
}
