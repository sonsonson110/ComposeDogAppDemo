package com.example.kotlindogapp.ui.screens.dogdetail

import com.example.kotlindogapp.model.Dog

data class DogDetailUiState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val dogDetail: Dog = Dog()
)