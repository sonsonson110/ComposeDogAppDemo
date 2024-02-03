package com.example.kotlindogapp.ui.screens.dogdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlindogapp.common.network.ApiState
import com.example.kotlindogapp.data.model.DogApiModel
import com.example.kotlindogapp.data.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dogRepository: DogRepository
) : ViewModel() {
    private val _dogId: String = checkNotNull(savedStateHandle["dogId"])

    private val _dogDetailUiState = MutableStateFlow(DogDetailUiState())
    val dogDetailUiState = _dogDetailUiState.asStateFlow()

    init {
        getDog()
    }

    private fun getDog() = viewModelScope.launch {
        _dogDetailUiState.update { it.copy(isLoading = true) }
        dogRepository.getDogById(_dogId)
            .collect { apiState ->
                when (apiState) {
                    is ApiState.Success<*> -> _dogDetailUiState.update {
                        it.copy(
                            dogDetail = apiState.data as DogApiModel,
                        )
                    }
                    is ApiState.Failure -> _dogDetailUiState.update { it.copy(error = apiState.e) }
                    else -> {}
                }
            }
        _dogDetailUiState.update { it.copy(isLoading = false) }
    }
}