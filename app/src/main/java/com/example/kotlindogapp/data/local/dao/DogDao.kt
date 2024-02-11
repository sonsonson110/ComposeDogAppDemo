package com.example.kotlindogapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.kotlindogapp.data.local.entity.DogEntity
import com.example.kotlindogapp.data.local.querymodel.DogWithBreeds

@Dao
interface DogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertDog(vararg dogEntity: DogEntity)

    @Transaction
    @Query("SELECT * FROM dog")
    suspend fun getDogWithBreeds(): List<DogWithBreeds>
}