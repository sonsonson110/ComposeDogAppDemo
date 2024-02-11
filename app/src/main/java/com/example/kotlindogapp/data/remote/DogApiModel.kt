package com.example.kotlindogapp.data.remote

import com.google.gson.annotations.SerializedName

data class DogApiModel(
    @SerializedName("breeds") var breeds: ArrayList<BreedApiModel> = arrayListOf(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null
)

data class BreedApiModel(
    @SerializedName("weight") var weight: WeightApiModel? = WeightApiModel(),
    @SerializedName("height") var height: HeightApiModel? = HeightApiModel(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("bred_for") var bredFor: String? = null,
    @SerializedName("breed_group") var breedGroup: String? = null,
    @SerializedName("life_span") var lifeSpan: String? = null,
    @SerializedName("temperament") var temperament: String? = null,
    @SerializedName("reference_image_id") var referenceImageId: String? = null
)

data class HeightApiModel(
    @SerializedName("imperial") var imperial: String? = null,
    @SerializedName("metric") var metric: String? = null
)

data class WeightApiModel(
    @SerializedName("imperial") var imperial: String? = null,
    @SerializedName("metric") var metric: String? = null
)
