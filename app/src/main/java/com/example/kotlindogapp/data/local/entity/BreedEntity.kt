package com.example.kotlindogapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kotlindogapp.model.Breed

@Entity(tableName = "breed")
data class BreedEntity(
    @PrimaryKey
    @ColumnInfo(name = "breed_id") val breedId: Int = 0,
    @ColumnInfo(name = "breed_name") val name: String? = null,
    @ColumnInfo(name = "bred_for") val bredFor: String? = null,
    @ColumnInfo(name = "breed_group") val breedGroup: String? = null,
    @ColumnInfo(name = "life_span") val lifeSpan: String? = null,
    @ColumnInfo(name = "temperament") val temperament: String? = null,
    @ColumnInfo(name = "reference_image_id") val referenceImageId: String? = null,
    @ColumnInfo(name = "country_code") val countryCode: String? = null,
    @ColumnInfo(name = "weight_imperial") val weightImperial: String? = null,
    @ColumnInfo(name = "weight_metric") val weightMetric: String? = null,
    @ColumnInfo(name = "height_imperial") val heightImperial: String? = null,
    @ColumnInfo(name = "height_metric") val heightMetric: String? = null,
) {
    companion object {
        fun fromBreed(breed: Breed): BreedEntity {
            return BreedEntity(
                breedId = breed.id!!,
                name = breed.name,
                bredFor = breed.bredFor,
                breedGroup = breed.breedGroup,
                lifeSpan = breed.lifeSpan,
                temperament = breed.temperament,
                referenceImageId = breed.referenceImageId,
                countryCode = breed.countryCode,
                weightImperial = breed.weight?.imperial,
                weightMetric = breed.weight?.metric,
                heightImperial = breed.height?.imperial,
                heightMetric = breed.height?.metric
            )
        }
    }
}