package com.example.kotlindogapp.nav

sealed class Screen(val route: String, val uri: String? = null) {
    data object DogList: Screen(route = "dog_list")
    data object DogDetail: Screen(route = "dog_detail", uri = "https://www.example.com")

    data object DogFavourite: Screen(route = "dog_favourite")
    data object Setting: Screen(route = "setting")
}