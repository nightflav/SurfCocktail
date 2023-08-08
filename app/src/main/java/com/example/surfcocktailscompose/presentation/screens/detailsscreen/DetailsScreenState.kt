package com.example.surfcocktailscompose.presentation.screens.detailsscreen

import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.util.Consts.CREATE_NEW_COCKTAIL_ID
import com.example.surfcocktailscompose.util.UiState

data class DetailsScreenState(
    val cocktail: CocktailDTO = CocktailDTO(
        id = CREATE_NEW_COCKTAIL_ID,
        ingredients = emptyArray()
    ),
    val isLoading: Boolean = true,
    val errorState: Boolean = false,
    val error: Throwable? = null
) : UiState
