package com.example.kotlindogapp.ui.screens.dogfavourite

import com.example.kotlindogapp.model.Dog

data class DogFavouriteUiState(
    val isLoading: Boolean? = null,
    val dogList: List<Dog> = emptyList(),
)
