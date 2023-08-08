package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.util.UiEvent

sealed class UserEditCocktailIntents(open val id: String) : UiEvent {

    data class Init(override val id: String) : UserEditCocktailIntents(id)
    data class SaveNewCocktail(
        val cocktail: CocktailDTO
    ) : UserEditCocktailIntents(cocktail.id)

    data class OpenDialog(override val id: String) : UserEditCocktailIntents(id)
    data class AddNewIngredient(override val id: String, val ingredient: String) : UserEditCocktailIntents(id)
    data class EditName(override val id: String, val name: String) : UserEditCocktailIntents(id)
    data class EditRecipe(override val id: String, val recipe: String) : UserEditCocktailIntents(id)
    data class EditDescription(override val id: String, val desc: String) : UserEditCocktailIntents(id)

}