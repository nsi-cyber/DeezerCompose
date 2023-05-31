package com.nsicyber.deezercompose.di

import com.nsicyber.deezercompose.Constants.BASE_URL
import com.nsicyber.deezercompose.DeezerApiRepository
import com.nsicyber.deezercompose.data.remote.retrofit.DeezerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDeezerRepository(api: DeezerApi) = DeezerApiRepository(api)

    @Singleton
    @Provides
    fun provideDeezerApi(): DeezerApi {
        return Retrofit.Builder().addConverterFactory((GsonConverterFactory.create()))
            .baseUrl(BASE_URL).build().create(DeezerApi::class.java)
    }

}