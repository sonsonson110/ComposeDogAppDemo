package com.example.kotlindogapp.ui.screens.dogdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.example.kotlindogapp.ui.screens.doglist.DogItem

@Composable
fun DogDetailScreen(navController: NavController, viewModel: DogDetailViewModel = hiltViewModel()) {
    val uiState by viewModel.dogDetailUiState.collectAsState()

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = { DogDetailTopBar(navController = navController) },
        content = { innerPadding ->
            DogDetailBody(
                uiState = uiState,
                modifier = Modifier.padding(innerPadding)
            )
        },
        floatingActionButton = { DogDetailFab(viewModel::addDog) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DogDetailTopBar(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = "Dog detail") },
        navigationIcon = {
            Icon(imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back button",
                modifier = Modifier
                    .clickable { navigateUp(navController) }
                    .padding(horizontal = 8.dp)
            )
        }
    )
}

@Composable
fun DogDetailFab(onSaveDogClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(onClick = { onSaveDogClick() }) {
        Icon(Icons.Outlined.Favorite, contentDescription = "Bookmark")
    }
}

@Composable
fun DogDetailBody(uiState: DogDetailUiState, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        if (uiState.error != null) {
            Text(text = "Failed to fetch: ${uiState.error}")
        }

        DogItem(
            dog = uiState.dogDetail, modifier = Modifier
                .scrollable(
                    state = rememberScrollState(), orientation = Orientation.Vertical
                )
        )
    }
}

fun navigateUp(navController: NavController) = navController.popBackStack()