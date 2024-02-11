package com.example.kotlindogapp.data.local.querymodel

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.kotlindogapp.data.local.entity.BreedEntity
import com.example.kotlindogapp.data.local.entity.DogBreedCrossRef
import com.example.kotlindogapp.data.local.entity.DogEntity

// Dog detail queries
data class DogWithBreeds(
    @Embedded val dog: DogEntity,
    @Relation(
        parentColumn = "dog_id",
        entityColumn = "breed_id",
        associateBy = Junction(
            DogBreedCrossRef::class
        )
    )
    val breeds: List<BreedEntity>
)