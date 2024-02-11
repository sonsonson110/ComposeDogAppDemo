package com.example.kotlindogapp.model

import com.example.kotlindogapp.data.local.entity.BreedEntity
import com.example.kotlindogapp.data.local.entity.DogEntity
import com.example.kotlindogapp.data.remote.BreedApiModel
import com.example.kotlindogapp.data.remote.DogApiModel
import com.example.kotlindogapp.data.remote.HeightApiModel
import com.example.kotlindogapp.data.remote.WeightApiModel

data class Dog(
    val breeds: ArrayList<Breed> = arrayListOf(),
    val id: String? = null,
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null,
) {
    companion object {
        fun fromDogApiModel(dogApiModel: DogApiModel): Dog {
            var dog = Dog()
            val breeds = arrayListOf<Breed>()
            for (breedApiModel in dogApiModel.breeds) {
                breeds.add(Breed.fromBreedApiModel(breedApiModel))
            }
            dogApiModel.apply {
                dog = Dog(
                    breeds = breeds,
                    id = id,
                    url = url,
                    width = width,
                    height = height,
                )
            }
            return dog
        }

        fun fromDogEntity(dogEntity: DogEntity): Dog {
            return Dog(
                id = dogEntity.id,
                url = dogEntity.url,
                width = dogEntity.width,
                height = dogEntity.height
            )
        }
    }
}

data class Breed(
    val weight: Weight? = Weight(),
    val height: Height? = Height(),
    val id: Int? = null,
    val name: String? = null,
    val bredFor: String? = null,
    val breedGroup: String? = null,
    val lifeSpan: String? = null,
    val temperament: String? = null,
    val referenceImageId: String? = null,
    val countryCode: String? = null,
) {
    companion object {
        fun fromBreedApiModel(breedApiModel: BreedApiModel?): Breed {
            var breed = Breed()
            breedApiModel?.apply {
                breed = Breed(
                    weight = Weight.fromWeightApiModel(weight),
                    height = Height.fromHeightApiModel(height),
                    id = id,
                    name = name,
                    bredFor = bredFor,
                    breedGroup = breedGroup,
                    lifeSpan = lifeSpan,
                    temperament = temperament,
                    referenceImageId = referenceImageId,
                )
            }
            return breed
        }

        fun fromBreedEntity(breedEntity: BreedEntity): Breed {
            return Breed(
                weight = Weight(
                    imperial = breedEntity.weightImperial,
                    metric = breedEntity.weightMetric
                ),
                height = Height(
                    imperial = breedEntity.heightImperial,
                    metric = breedEntity.heightMetric
                ),
                id = breedEntity.breedId,
                name = breedEntity.name,
                bredFor = breedEntity.bredFor,
                breedGroup = breedEntity.breedGroup,
                lifeSpan = breedEntity.lifeSpan,
                temperament = breedEntity.temperament,
                referenceImageId = breedEntity.referenceImageId,
                countryCode = breedEntity.countryCode
            )
        }
    }
}

data class Weight(
    val imperial: String? = null,
    val metric: String? = null,
) {
    companion object {
        fun fromWeightApiModel(weightApiModel: WeightApiModel?): Weight {
            var weight: Weight = Weight()
            weightApiModel?.apply {
                weight = Weight(
                    imperial = imperial,
                    metric = metric
                )
            }
            return weight
        }
    }
}

data class Height(
    val imperial: String? = null,
    val metric: String? = null,
) {
    companion object {
        fun fromHeightApiModel(heightApiModel: HeightApiModel?): Height {
            var height: Height = Height();
            heightApiModel?.apply {
                height = Height(
                    imperial = imperial,
                    metric = metric
                )
            }
            return height
        }
    }
}