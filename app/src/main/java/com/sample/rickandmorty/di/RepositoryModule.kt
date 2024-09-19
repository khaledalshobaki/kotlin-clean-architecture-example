package com.sample.rickandmorty.di

import com.sample.rickandmorty.data.repository.DefaultCharacterRepository
import com.sample.rickandmorty.data.source.local.dao.CharacterDao
import com.sample.rickandmorty.data.source.network.service.CharacterService
import com.sample.rickandmorty.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideJobRepository(
        characterDao: CharacterDao,
        characterService: CharacterService
    ): CharacterRepository {
        return DefaultCharacterRepository(
            characterDao = characterDao,
            characterService = characterService
        )
    }
}