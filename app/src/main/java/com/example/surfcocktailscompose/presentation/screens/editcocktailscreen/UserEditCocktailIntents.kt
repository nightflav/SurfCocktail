package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.util.UiEvent

sealed class UserEditCocktailIntents() : UiEvent {

    data class Init(val id: String) : UserEditCocktailIntents()
    data object SaveNewCocktail : UserEditCocktailIntents()

    data class OpenDialog(val id: String) : UserEditCocktailIntents()
    data class AddNewIngredient(val id: String, val ingredient: String) :
        UserEditCocktailIntents()

    data class EditName(val id: String, val name: String) : UserEditCocktailIntents()
    data class EditRecipe(val id: String, val recipe: String) : UserEditCocktailIntents()
    data class EditDescription(val id: String, val desc: String) :
        UserEditCocktailIntents()

}