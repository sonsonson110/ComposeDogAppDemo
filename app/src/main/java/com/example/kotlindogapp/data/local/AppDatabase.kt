package com.example.kotlindogapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlindogapp.data.local.dao.BreedDao
import com.example.kotlindogapp.data.local.dao.DogBreedDao
import com.example.kotlindogapp.data.local.dao.DogDao
import com.example.kotlindogapp.data.local.entity.BreedEntity
import com.example.kotlindogapp.data.local.entity.DogBreedCrossRef
import com.example.kotlindogapp.data.local.entity.DogEntity

@Database(entities = [DogEntity::class, BreedEntity::class, DogBreedCrossRef::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dogDao(): DogDao
    abstract fun breedDao(): BreedDao
    abstract fun dogBreedDao(): DogBreedDao
}