package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

sealed interface UserEditCocktailIntents {
    data object SaveNewCocktail : UserEditCocktailIntents
    data object AddNewIngredient : UserEditCocktailIntents
    data object AddPhotoToCurrentCocktail : UserEditCocktailIntents
}