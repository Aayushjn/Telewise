package com.aayush.telewise.di

import android.content.Context
import com.aayush.telewise.api.service.MovieApi
import com.aayush.telewise.repository.MovieRepository
import com.aayush.telewise.util.android.getOkHttpClient
import com.aayush.telewise.util.common.BASE_URL
import com.aayush.telewise.util.common.JSON
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideMovieApi(@ApplicationContext context: Context): MovieApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(JSON.asConverterFactory("application/json".toMediaType()))
            .client(getOkHttpClient(context))
            .build()
            .create()

    @Singleton
    @Provides
    fun provideMovieRepository(movieApi: MovieApi): MovieRepository = MovieRepository(movieApi)
}
