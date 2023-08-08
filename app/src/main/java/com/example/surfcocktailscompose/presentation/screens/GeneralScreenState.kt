package com.example.surfcocktailscompose.presentation.screens

sealed class GeneralScreenState {
    data object Loading : GeneralScreenState()
    data object Loaded : GeneralScreenState()
}
