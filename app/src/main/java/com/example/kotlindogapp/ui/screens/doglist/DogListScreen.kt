package com.example.kotlindogapp.ui.screens.doglist

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlindogapp.common.network.ApiState

@Composable
fun DogListScreen(viewModel: DogListViewModel = viewModel()) {
    // initialize data
    val uiState by viewModel.dogListState.collectAsState()

    when (uiState) {
        is ApiState.Empty -> {
            Text(text = "No data retrieved")
        }

        is ApiState.Loading -> {
            CircularProgressIndicator()
        }

        is ApiState.Failure -> {
            Text(text = "Failed to fetch: ${(uiState as ApiState.Failure).e}")
        }

        is ApiState.Success -> {
            Text(text = (uiState as ApiState.Success).data.toString())
        }
    }
}