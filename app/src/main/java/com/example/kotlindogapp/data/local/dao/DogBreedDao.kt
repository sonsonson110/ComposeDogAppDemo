package com.example.kotlindogapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.kotlindogapp.data.local.entity.DogBreedCrossRef

@Dao
interface DogBreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertDogBreed(vararg dogBreedCrossRef: DogBreedCrossRef)
}