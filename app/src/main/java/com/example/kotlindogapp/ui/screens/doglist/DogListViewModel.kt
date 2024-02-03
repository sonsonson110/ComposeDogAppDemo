package com.example.kotlindogapp.ui.screens.doglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlindogapp.common.network.ApiState
import com.example.kotlindogapp.data.model.DogApiModel
import com.example.kotlindogapp.data.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(private val repository: DogRepository) : ViewModel() {
    private val _dogListState = MutableStateFlow<DogListUiState>(DogListUiState())
    val dogListState = _dogListState.asStateFlow()

    init {
        getDogList()
    }

    private fun getDogList() = viewModelScope.launch {
        _dogListState.update { it.copy(isLoading = true) }
        repository.getDogList()
            .collect { apiState ->
                when (apiState) {
                    is ApiState.Success<*> -> _dogListState.update {
                        it.copy(
                            dogList = apiState.data as List<DogApiModel>,
                        )
                    }
                    is ApiState.Failure -> _dogListState.update { it.copy(error = apiState.e) }
                    is ApiState.Empty -> _dogListState.update { it.copy(isEmpty = true) }
                    else -> {}
                }
            }
        _dogListState.update { it.copy(isLoading = false) }
    }
}