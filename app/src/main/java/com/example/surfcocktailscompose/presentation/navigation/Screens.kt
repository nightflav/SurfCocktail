package com.example.surfcocktailscompose.presentation.navigation

sealed class Screens(
    val route: String,
) {
    object HomeScreen : Screens(route = "home_screen")
    object DetailsScreen : Screens(route = "details_screen/{id}")
    object EditCocktailScreen : Screens(route = "create_new_cocktail_screen/{id}")
}
