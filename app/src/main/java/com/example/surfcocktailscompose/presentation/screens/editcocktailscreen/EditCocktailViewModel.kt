package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.data.repository.CocktailsRepository
import com.example.surfcocktailscompose.util.BaseViewModel
import com.example.surfcocktailscompose.util.Consts.CREATE_NEW_COCKTAIL_ID
import com.example.surfcocktailscompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditCocktailViewModel @Inject constructor(
    private val cocktailsRepo: CocktailsRepository
) : BaseViewModel<EditCocktailScreenState, UserEditCocktailIntents>() {

    private val screenState = MutableStateFlow(EditCocktailScreenState())
    override val state: Flow<EditCocktailScreenState> = screenState.asStateFlow()
    private val currState
        get() = screenState.value

    private lateinit var startState: EditCocktailScreenState

    fun sendEvent(event: UserEditCocktailIntents) {
        viewModelScope.launch {
            when (event) {
                is UserEditCocktailIntents.Init -> {
                    initialize(event.id)
                }

                UserEditCocktailIntents.AddNewIngredient -> {
                    addNewIngredient()
                }

                is UserEditCocktailIntents.EditDescription -> {
                    editDescription(event.desc)
                }

                is UserEditCocktailIntents.EditName -> {
                    editName(event.name)
                }

                is UserEditCocktailIntents.EditRecipe -> {
                    editRecipe(event.recipe)
                }

                is UserEditCocktailIntents.SaveNewCocktail -> {
                    saveCocktail()
                }

                is UserEditCocktailIntents.OpenDialog -> {
                    openDialog()
                }

                UserEditCocktailIntents.CloseDialog -> {
                    closeDialog()
                }

                is UserEditCocktailIntents.EditCurrentIngredient -> {
                    editCurrIngredient(event.ingredient)
                }

                is UserEditCocktailIntents.DeleteIngredient -> {
                    deleteIngredient(event.ingredient)
                }

                UserEditCocktailIntents.CancelEditing -> {
                    returnToStart()
                }
            }
        }
    }

    private suspend fun returnToStart() {
        screenState.emit(startState)
    }

    private suspend fun deleteIngredient(ing: String) {
        val newIngredients = currState.ingredients.toMutableList()
        newIngredients.remove(ing)
        cocktailsRepo.addCocktailToDb(
            CocktailDTO(
                id = currState.id,
                name = currState.name,
                description = currState.description,
                recipe = currState.recipe,
                ingredients = newIngredients.toTypedArray()
            )
        )
        screenState.emit(
            currState.copy(
                ingredients = newIngredients.toTypedArray()
            )
        )
    }

    private suspend fun editCurrIngredient(ingredient: String) {
        screenState.emit(
            currState.copy(
                currIngredient = ingredient
            )
        )
    }

    private suspend inline fun closeDialog() =
        screenState.emit(
            currState.copy(
                showDialog = false, isLoading = false, errorState = false, error = null
            )
        )

    private suspend inline fun openDialog() =
        screenState.emit(
            currState.copy(
                showDialog = true, isLoading = false, errorState = false, error = null
            )
        )

    private suspend inline fun saveCocktail() {
        cocktailsRepo.addCocktailToDb(
            cocktailDTO = CocktailDTO(
                id = currState.id,
                name = currState.name,
                description = currState.description,
                recipe = currState.recipe,
                ingredients = currState.ingredients
            )
        )
    }

    private suspend fun editRecipe(recipe: String) {
        screenState.emit(
            currState.copy(
                recipe = recipe
            )
        )
    }

    private suspend fun editName(name: String) {
        screenState.emit(
            currState.copy(
                name = name
            )
        )
    }

    private suspend fun editDescription(disc: String) {
        screenState.emit(
            currState.copy(
                description = disc
            )
        )
    }

    private suspend fun addNewIngredient() {
        screenState.emit(
            currState.copy(
                ingredients = currState.ingredients.toMutableList().apply {
                    this.add(currState.currIngredient)
                }.toTypedArray(),
                showDialog = false,
                currIngredient = ""
            )
        )
    }

    private suspend fun initialize(initialId: String) {
        val id = System.currentTimeMillis().hashCode().toString()
        Log.d("TAGTAGTAG", "Initializing with initialId = $initialId")
        val cocktail =
            if (initialId != CREATE_NEW_COCKTAIL_ID) cocktailsRepo.loadCocktailByIdFromDB(initialId)
            else flowOf(
                Resource.Success(
                    CocktailDTO(
                        id = id
                    )
                )
            )
        Log.d("TAGTAGTAG", "Got $cocktail")
        cocktail.collect {
            Log.d("TAGTAGTAG", "Cocktail is $it")
            if (it is Resource.Success) {
                val newCocktail = it.data
                Log.d("TAGTAGTAG", "Here got some $newCocktail")
                val state = currState.copy(
                    id = newCocktail.id,
                    name = newCocktail.name ?: "",
                    description = newCocktail.description ?: "",
                    recipe = newCocktail.recipe ?: "",
                    ingredients = newCocktail.ingredients ?: emptyArray(),
                    isLoading = false,
                    error = null,
                    errorState = false,
                )
                startState = state
                screenState.emit(
                    state
                )
            }
        }
    }
}