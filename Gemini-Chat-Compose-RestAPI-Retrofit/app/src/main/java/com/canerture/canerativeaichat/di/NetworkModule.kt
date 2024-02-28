package com.canerture.canerativeaichat.di

import com.canerture.canerativeaichat.data.source.GenAiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val originalRequest = chain.request()

            val newRequest = originalRequest.newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()

            chain.proceed(newRequest)
        }
    }.build()

    @Provides
    @Singleton
    fun provideGenAiService(okHttpClient: OkHttpClient): GenAiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GenAiService::class.java)
}