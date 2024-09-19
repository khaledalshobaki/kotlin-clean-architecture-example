package com.sample.rickandmorty.di

import com.sample.rickandmorty.BuildConfig
import com.sample.rickandmorty.data.source.network.service.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesSocketOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideAppApiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .callFactory { okHttpClient.newCall(it) }
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }
}
