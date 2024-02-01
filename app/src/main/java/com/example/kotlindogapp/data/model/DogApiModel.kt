package com.example.kotlindogapp.data.model

import com.google.gson.annotations.SerializedName

data class DogApiModel(
    @SerializedName("breeds") var breeds: ArrayList<Breeds> = arrayListOf(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null
)

data class Breeds(
    @SerializedName("weight") var weight: Weight? = Weight(),
    @SerializedName("height") var height: Height? = Height(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("bred_for") var bredFor: String? = null,
    @SerializedName("breed_group") var breedGroup: String? = null,
    @SerializedName("life_span") var lifeSpan: String? = null,
    @SerializedName("temperament") var temperament: String? = null,
    @SerializedName("reference_image_id") var referenceImageId: String? = null
)

data class Height(
    @SerializedName("imperial") var imperial: String? = null,
    @SerializedName("metric") var metric: String? = null
)

data class Weight(
    @SerializedName("imperial") var imperial: String? = null,
    @SerializedName("metric") var metric: String? = null
)
