package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import com.example.surfcocktailscompose.util.Consts.CREATE_NEW_COCKTAIL_ID
import com.example.surfcocktailscompose.util.UiState

data class EditCocktailScreenState(
    val id: String = CREATE_NEW_COCKTAIL_ID,
    val name: String = "",
    val description: String = "",
    val ingredients: Array<String> = emptyArray(),
    val recipe: String = "",
    val isLoading: Boolean = true,
    val errorState: Boolean = false,
    val error: Throwable? = null,
    val showDialog: Boolean = false
) : UiState