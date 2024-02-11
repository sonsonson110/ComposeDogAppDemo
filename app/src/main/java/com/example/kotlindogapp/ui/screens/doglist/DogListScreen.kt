package com.example.kotlindogapp.ui.screens.doglist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.kotlindogapp.model.Dog
import com.example.kotlindogapp.nav.Screen

@Composable
fun DogListScreen(
    viewModel: DogListViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // initialize data
    val uiState by viewModel.dogListState.collectAsState()

    Scaffold(
        topBar = { DogListTopBar(navController = navController) },
        content = { innerPadding ->
            DogListBody(
                uiState = uiState,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DogListTopBar(navController: NavController = rememberNavController(), modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(text = "Daily doge") },
        actions = {
            Icon(
                Icons.Outlined.FavoriteBorder,
                contentDescription = "Favourite dog",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { navigateToDogFavourite(navController) }
            )
        }
    )
}

fun navigateToDogFavourite(navController: NavController) {
    navController.navigate(route = Screen.DogFavourite.route)
}

@Composable
fun DogListBody(
    uiState: DogListUiState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    if (uiState.isEmpty) {
        Text(text = "No data retrieved")
    }

    if (uiState.isLoading) {
        CircularProgressIndicator()
    }

    if (uiState.error != null) {
        Text(text = "Failed to fetch: ${uiState.error}")
    }

    LazyColumn(modifier = modifier) {
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
fun DogItem(dog: Dog, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        GlideImage(
            model = dog.url,
            contentDescription = "a dog image",
            modifier = modifier.fillMaxWidth()
        )
        Text(text = dog.toString())
    }
}

fun navigateToDogDetail(dog: Dog, navController: NavController) =
    navController.navigate(route = "${Screen.DogDetail.route}/${dog.id}")