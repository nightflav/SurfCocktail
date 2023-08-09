package com.example.surfcocktailscompose.presentation.screens.homescreen

import com.example.surfcocktailscompose.util.UiEvent

sealed interface UserHomeIntents : UiEvent {
    data class SelectExistingCocktail(val id: String) : UserHomeIntents
    data object CreateNewCocktail : UserHomeIntents
    data object DeleteAllCocktails : UserHomeIntents
}