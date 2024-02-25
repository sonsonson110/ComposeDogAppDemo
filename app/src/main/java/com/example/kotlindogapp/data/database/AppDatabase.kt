package com.example.kotlindogapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlindogapp.data.database.dao.BreedDao
import com.example.kotlindogapp.data.database.dao.DogBreedDao
import com.example.kotlindogapp.data.database.dao.DogDao
import com.example.kotlindogapp.data.database.entity.BreedEntity
import com.example.kotlindogapp.data.database.entity.DogBreedCrossRef
import com.example.kotlindogapp.data.database.entity.DogEntity

@Database(entities = [DogEntity::class, BreedEntity::class, DogBreedCrossRef::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dogDao(): DogDao
    abstract fun breedDao(): BreedDao
    abstract fun dogBreedDao(): DogBreedDao
}