package com.example.kotlindogapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kotlindogapp.model.Dog

@Entity(tableName = "dog")
data class DogEntity (
    @PrimaryKey
    @ColumnInfo(name = "dog_id") val id: String = "",
    @ColumnInfo(name = "dog_url") val url: String? = null,
    @ColumnInfo(name = "dog_width") val width: Int? = null,
    @ColumnInfo(name = "dog_height") val height: Int? = null,
) {
    companion object {
        fun fromDog(dog: Dog): DogEntity {
            return DogEntity(
                id = dog.id!!,
                url = dog.url,
                width = dog.width,
                height = dog.height
            )
        }
    }
}