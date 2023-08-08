package com.example.surfcocktailscompose.presentation.screens.homescreen

import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.util.UiState

data class HomeScreenState(
    val cocktails: List<CocktailDTO> = emptyList(),
    val isLoading: Boolean = true,
    val errorState: Boolean = false,
    var error: Exception? = null
) : UiState
