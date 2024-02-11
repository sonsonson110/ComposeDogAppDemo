package com.example.kotlindogapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.kotlindogapp.data.local.entity.BreedEntity

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertBreed(vararg breedEntity: BreedEntity)
}