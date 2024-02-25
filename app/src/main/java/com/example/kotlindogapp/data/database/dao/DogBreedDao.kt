package com.example.kotlindogapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.kotlindogapp.data.database.entity.DogBreedCrossRef

@Dao
interface DogBreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertDogBreed(vararg dogBreedCrossRef: DogBreedCrossRef)
}