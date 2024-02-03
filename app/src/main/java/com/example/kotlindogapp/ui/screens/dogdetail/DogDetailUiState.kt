package com.example.kotlindogapp.ui.screens.dogdetail

import com.example.kotlindogapp.data.model.DogApiModel

data class DogDetailUiState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val dogDetail: DogApiModel = DogApiModel()
)