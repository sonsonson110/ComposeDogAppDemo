package com.example.kotlindogapp.data.repository

import com.example.kotlindogapp.common.network.ApiState
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
    fun getDogList(): Flow<ApiState>
    fun getDogById(dogId: String): Flow<ApiState>
}

class DogRepositoryImpl @Inject constructor(
    private val dogApiService: DogApiService
): DogRepository {

    override fun getDogList() = flow {
        try {
            val response = dogApiService.getDogList()
            if (response.isEmpty())
                emit(ApiState.Empty)
            else
                emit(ApiState.Success(data = response))
        } catch (e: Exception) {
            emit(ApiState.Failure(e = e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getDogById(dogId: String): Flow<ApiState> = flow {
        try {
            val response = dogApiService.getDogById(dogId)
            emit(ApiState.Success(data = response))
        } catch (e: Exception) {
            emit(ApiState.Failure(e = e))
        }
    }.flowOn(Dispatchers.IO)
}