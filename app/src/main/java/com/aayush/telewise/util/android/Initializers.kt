package com.aayush.telewise.util.android

import android.content.Context
import androidx.startup.Initializer
import com.aayush.telewise.BuildConfig
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) =
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

class ThreeTenABPInitializer : Initializer<Unit> {
    override fun create(context: Context) = AndroidThreeTen.init(context)

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
