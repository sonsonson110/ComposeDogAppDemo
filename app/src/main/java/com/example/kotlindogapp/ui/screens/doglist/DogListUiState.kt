package com.example.kotlindogapp.ui.screens.doglist

import com.example.kotlindogapp.data.model.DogApiModel

data class DogListUiState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = false,
    val error: Throwable? = null,
    val dogList: List<DogApiModel> = emptyList(),
)