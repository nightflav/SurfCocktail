package com.example.surfcocktailscompose.presentation.navigation

sealed class Screens(
    val route: String,
    val argumentlessRoute: String
) {
    object HomeScreen : Screens(route = "home_screen", argumentlessRoute = "home_screen")
    object DetailsScreen :
        Screens(route = "details_screen/{id}", argumentlessRoute = "details_screen")

    object EditCocktailScreen : Screens(
        route = "create_new_cocktail_screen/{id}",
        argumentlessRoute = "create_new_cocktail_screen"
    )
}
