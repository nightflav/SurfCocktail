package com.example.surfcocktailscompose.presentation.screens.detailsscreen

import com.example.surfcocktailscompose.util.UiEvent

sealed interface UserDetailsIntents : UiEvent {
    data class EditCurrentCocktail(val id: String) : UserDetailsIntents
}