package com.canerture.canerativeaichat.di

import com.canerture.canerativeaichat.BuildConfig
import com.canerture.canerativeaichat.data.source.GenAiService
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGenAiService(): GenAiService = GenAiService(
        textModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.GEMINI_API_KEY,
        ),
        visionModel = GenerativeModel(
            modelName = "gemini-pro-vision",
            apiKey = BuildConfig.GEMINI_API_KEY,
        ),
    )
}