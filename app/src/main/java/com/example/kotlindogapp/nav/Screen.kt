package com.example.kotlindogapp.nav

sealed class Screen(val route: String) {
    data object DogList: Screen(route = "dog_list")
    data object DogDetail: Screen(route = "dog_detail")

    data object DogFavourite: Screen(route = "dog_favourite")
}