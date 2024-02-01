package com.example.kotlindogapp.data.repository

import com.example.kotlindogapp.data.api.DogApiService
import com.example.kotlindogapp.data.model.DogApiModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DogRepository {
    fun getDogList(): Flow<List<DogApiModel>>
}

class DogRepositoryImpl @Inject constructor(
    private val dogApiService: DogApiService
): DogRepository {

    override fun getDogList() = flow {
        val response = dogApiService.getDogList()
        emit(response)
    }.flowOn(Dispatchers.IO)
}