package com.canerture.canerativeaichat.di

import com.canerture.canerativeaichat.data.repository.GenAiRepository
import com.canerture.canerativeaichat.data.source.GenAiService
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
    fun provideSatelliteRepository(
        service: GenAiService,
    ): GenAiRepository = GenAiRepository(service)
}