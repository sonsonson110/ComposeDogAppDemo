package com.example.kotlindogapp.di

import com.example.kotlindogapp.data.repository.DogRepository
import com.example.kotlindogapp.data.repository.DogRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDogRepository(
        dogRepositoryImpl: DogRepositoryImpl
    ): DogRepository
}