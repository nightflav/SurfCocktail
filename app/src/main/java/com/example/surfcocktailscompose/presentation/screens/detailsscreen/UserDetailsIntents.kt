package com.example.surfcocktailscompose.presentation.screens.detailsscreen

sealed interface UserDetailsIntents {
    data class EditCurrentCocktail(val id: String) : UserDetailsIntents
}