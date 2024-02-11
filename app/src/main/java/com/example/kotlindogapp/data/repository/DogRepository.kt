package com.example.kotlindogapp.data.repository

import androidx.room.withTransaction
import com.example.kotlindogapp.common.network.ApiState
import com.example.kotlindogapp.data.local.AppDatabase
import com.example.kotlindogapp.data.local.dao.BreedDao
import com.example.kotlindogapp.data.local.dao.DogBreedDao
import com.example.kotlindogapp.data.local.dao.DogDao
import com.example.kotlindogapp.data.local.entity.BreedEntity
import com.example.kotlindogapp.data.local.entity.DogBreedCrossRef
import com.example.kotlindogapp.data.local.entity.DogEntity
import com.example.kotlindogapp.data.remote.DogApiService
import com.example.kotlindogapp.model.Breed
import com.example.kotlindogapp.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface DogRepository {
    suspend fun addLocalDog(dog: Dog)
    suspend fun getLocalDogList(): Flow<List<Dog>>
    suspend fun getRemoteDogList(): Flow<ApiState>
    suspend fun getRemoteDogById(dogId: String): Flow<ApiState>
}

class DogRepositoryImpl @Inject constructor(
    private val dogApiService: DogApiService,
    private val appDatabase: AppDatabase,
    private val dogDao: DogDao,
    private val breedDao: BreedDao,
    private val dogBreedDao: DogBreedDao
) : DogRepository {
    override suspend fun addLocalDog(dog: Dog) {
        appDatabase.withTransaction {
            dogDao.insertDog(DogEntity.fromDog(dog))
            for (breed in dog.breeds) {
                breedDao.insertBreed(BreedEntity.fromBreed(breed))
                dogBreedDao.insertDogBreed(DogBreedCrossRef(dogId = dog.id!!, breedId = breed.id!!))
            }

        }
    }

    override suspend fun getLocalDogList(): Flow<List<Dog>> = flow {
        val dogList = dogDao.getDogWithBreeds()
        val resultList = arrayListOf<Dog>()
        for (e in dogList) {
            val dog = Dog.fromDogEntity(e.dog)
            for (breed in e.breeds)
                dog.breeds.add(Breed.fromBreedEntity(breed))

            resultList.add(dog)
        }
        emit(resultList)
    }

    override suspend fun getRemoteDogList() = flow {
        try {
            val response = dogApiService.getDogList()
            if (response.isEmpty())
                emit(ApiState.Empty)
            else {
                val dogList = arrayListOf<Dog>()
                for (dogApiModel in response) {
                    dogList.add(Dog.fromDogApiModel(dogApiModel))
                }
                emit(ApiState.Success(data = dogList))
            }
        } catch (e: Exception) {
            emit(ApiState.Failure(e = e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getRemoteDogById(dogId: String): Flow<ApiState> = flow {
        try {
            val response = dogApiService.getDogById(dogId)
            emit(ApiState.Success(data = Dog.fromDogApiModel(response)))
        } catch (e: Exception) {
            emit(ApiState.Failure(e = e))
        }
    }.flowOn(Dispatchers.IO)
}