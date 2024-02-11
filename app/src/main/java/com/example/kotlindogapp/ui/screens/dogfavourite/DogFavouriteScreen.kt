package com.example.kotlindogapp.ui.screens.dogfavourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kotlindogapp.ui.screens.dogdetail.navigateUp
import com.example.kotlindogapp.ui.screens.doglist.DogItem
import com.example.kotlindogapp.ui.screens.doglist.navigateToDogDetail

@Composable
fun DogFavouriteScreen(
    navController: NavController,
    viewModel: DogFavouriteViewModel = hiltViewModel()
) {
    val uiState by viewModel.dogFavouriteUiState.collectAsState()
    Scaffold(
        topBar = { DogFavouriteAppBar(navController = navController) },
        content = { innerPadding ->
            DogFavouriteBody(
                uiState = uiState,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogFavouriteAppBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = "Favourite dog") },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "back button",
                modifier = Modifier
                    .clickable { navigateUp(navController = navController) }
                    .padding(8.dp)
            )
        }
    )
}

@Composable
fun DogFavouriteBody(
    uiState: DogFavouriteUiState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (uiState.isLoading != true) {
            LazyColumn {
                itemsIndexed(uiState.dogList) { _, dog ->
                    DogItem(
                        dog = dog,
                        modifier = Modifier.clickable { navigateToDogDetail(dog, navController) }
                    )
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }
}


