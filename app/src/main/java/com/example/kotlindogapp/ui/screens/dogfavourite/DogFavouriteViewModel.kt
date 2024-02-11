package com.example.kotlindogapp.ui.screens.dogfavourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlindogapp.data.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogFavouriteViewModel @Inject constructor(
    private val dogRepository: DogRepository
) : ViewModel() {
    private val _dogFavouriteUiState = MutableStateFlow(DogFavouriteUiState())
    val dogFavouriteUiState = _dogFavouriteUiState.asStateFlow()

    init {
        getLocalDogList()
    }

    private fun getLocalDogList() = viewModelScope.launch {
        _dogFavouriteUiState.update { it.copy(isLoading = true) }
        dogRepository.getLocalDogList()
            .collect { dogList -> _dogFavouriteUiState.update { it.copy(dogList = dogList) } }
        _dogFavouriteUiState.update { it.copy(isLoading = false) }
    }
}