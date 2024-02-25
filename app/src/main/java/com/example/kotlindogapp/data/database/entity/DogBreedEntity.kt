package com.example.kotlindogapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "dog_breed", primaryKeys = ["dog_id", "breed_id"],
    foreignKeys = [
        ForeignKey(
            entity = DogEntity::class,
            parentColumns = ["dog_id"],
            childColumns = ["dog_id"]
        ),
        ForeignKey(
            entity = BreedEntity::class,
            parentColumns = ["breed_id"],
            childColumns = ["breed_id"]
        )
    ])
data class DogBreedCrossRef(
    @ColumnInfo(name = "dog_id") val dogId: String,
    @ColumnInfo(name = "breed_id") val breedId: Int
)