package com.aayush.telewise.di

import android.content.Context
import com.aayush.telewise.util.android.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences = AppPreferences(context)
}
