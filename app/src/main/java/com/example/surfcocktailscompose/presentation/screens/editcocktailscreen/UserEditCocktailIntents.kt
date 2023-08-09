package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.util.UiEvent

sealed interface UserEditCocktailIntents : UiEvent {

    data class Init(val id: String) : UserEditCocktailIntents
    data object SaveNewCocktail : UserEditCocktailIntents

    data object OpenDialog : UserEditCocktailIntents
    data object CloseDialog : UserEditCocktailIntents
    data class EditCurrentIngredient(val ingredient: String) : UserEditCocktailIntents
    data object AddNewIngredient :
        UserEditCocktailIntents

    data class EditName(val name: String) : UserEditCocktailIntents
    data class EditRecipe(val recipe: String) : UserEditCocktailIntents
    data class EditDescription(val desc: String) :
        UserEditCocktailIntents

    data class DeleteIngredient(val ingredient: String) : UserEditCocktailIntents

}