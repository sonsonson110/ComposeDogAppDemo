package com.example.kotlindogapp.ui.screens.doglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlindogapp.common.network.ApiState
import com.example.kotlindogapp.data.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(private val repository: DogRepository): ViewModel() {
    private val _dogListState = MutableStateFlow<ApiState>(ApiState.Empty)
    val dogListState = _dogListState.asStateFlow()

    init {
        getDogList()
    }

    private fun getDogList() = viewModelScope.launch {
        _dogListState.update { ApiState.Loading }
        repository.getDogList()
            .catch { e ->
                _dogListState.update { ApiState.Failure(e) }
            }
            .collect { data ->
                _dogListState.update { ApiState.Success(data) }
            }
    }
}