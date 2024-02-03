package com.example.kotlindogapp.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kotlindogapp.ui.screens.dogdetail.DogDetailScreen
import com.example.kotlindogapp.ui.screens.doglist.DogListScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.DogList.route) {
        composable(
            route = Screen.DogList.route
        ) { DogListScreen(navController = navController) }

        composable(
            route = "${Screen.DogDetail.route}/{dogId}",
            arguments = listOf(navArgument("dogId") { type = NavType.StringType })
        ) { _ -> DogDetailScreen(navController = navController) }
    }
}