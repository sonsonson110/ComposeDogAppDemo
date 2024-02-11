package com.example.kotlindogapp.ui.screens.doglist

import com.example.kotlindogapp.model.Dog

data class DogListUiState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = false,
    val error: Throwable? = null,
    val dogList: List<Dog> = emptyList(),
)