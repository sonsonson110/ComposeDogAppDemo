package com.example.kotlindogapp.di

import com.example.kotlindogapp.data.local.AppDatabase
import com.example.kotlindogapp.data.local.dao.BreedDao
import com.example.kotlindogapp.data.local.dao.DogBreedDao
import com.example.kotlindogapp.data.local.dao.DogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun provideDogDao(
        database: AppDatabase,
    ): DogDao = database.dogDao()

    @Provides
    fun provideBreedDao(
        database: AppDatabase,
    ): BreedDao = database.breedDao()

    @Provides
    fun provideDogBreedDao(
        database: AppDatabase,
    ): DogBreedDao = database.dogBreedDao()
}