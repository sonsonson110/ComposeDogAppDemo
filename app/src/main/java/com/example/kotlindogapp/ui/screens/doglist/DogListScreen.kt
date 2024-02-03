package com.example.kotlindogapp.ui.screens.doglist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.kotlindogapp.common.network.ApiState
import com.example.kotlindogapp.data.model.DogApiModel
import com.example.kotlindogapp.nav.Screen

@Composable
fun DogListScreen(
    viewModel: DogListViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // initialize data
    val uiState by viewModel.dogListState.collectAsState()

    if (uiState.isEmpty) {
        Text(text = "No data retrieved")
    }

    if (uiState.isLoading) {
        CircularProgressIndicator()
    }

    if (uiState.error != null) {
        Text(text = "Failed to fetch: ${uiState.error}")
    }

    LazyColumn {
        itemsIndexed(uiState.dogList) { _, dog ->
            DogItem(
                dog = dog,
                modifier = Modifier.clickable { navigateToDogDetail(dog, navController) }
            )
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DogItem(dog: DogApiModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        GlideImage(
            model = dog.url,
            contentDescription = "a dog image",
            modifier = modifier.fillMaxWidth()
        )
        Text(text = dog.toString())
    }
}

fun navigateToDogDetail(dog: DogApiModel, navController: NavController) =
    navController.navigate(route = "${Screen.DogDetail.route}/${dog.id}")