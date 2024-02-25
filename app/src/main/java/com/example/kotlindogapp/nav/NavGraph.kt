package com.example.kotlindogapp.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.kotlindogapp.ui.screens.dogdetail.DogDetailScreen
import com.example.kotlindogapp.ui.screens.dogfavourite.DogFavouriteScreen
import com.example.kotlindogapp.ui.screens.doglist.DogListScreen
import com.example.kotlindogapp.ui.screens.setting.SettingScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.DogList.route) {
        composable(
            route = Screen.DogList.route
        ) { DogListScreen(navController = navController) }

        composable(
            route = "${Screen.DogDetail.route}/{dogId}",
            arguments = listOf(navArgument("dogId") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "${Screen.DogDetail.uri}/{dogId}" })
        ) { _ -> DogDetailScreen(navController = navController) }

        composable(
            route = Screen.DogFavourite.route
        ) { DogFavouriteScreen(navController = navController) }

        composable(
            route = Screen.Setting.route
        ) { SettingScreen(onNavigateUp = { navController.popBackStack() }) }
    }
}