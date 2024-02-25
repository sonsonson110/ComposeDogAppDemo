package com.example.kotlindogapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.kotlindogapp.data.database.entity.BreedEntity

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertBreed(vararg breedEntity: BreedEntity)
}