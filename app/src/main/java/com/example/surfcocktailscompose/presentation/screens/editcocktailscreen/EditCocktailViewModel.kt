package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.data.repository.CocktailsRepository
import com.example.surfcocktailscompose.util.BaseViewModel
import com.example.surfcocktailscompose.util.Consts.CREATE_NEW_COCKTAIL_ID
import com.example.surfcocktailscompose.util.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
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

    val events = Channel<UserEditCocktailIntents>()

    init {
        subscribeForIntents()
    }

    @OptIn(FlowPreview::class)
    private fun subscribeForIntents() {
        viewModelScope.launch {
            events.consumeAsFlow()
                .collectLatest {
                val event = it
                when (event) {
                    is UserEditCocktailIntents.Init -> {
                        Log.d("TAGTAGTAG", "got event with id ${event.id}")
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
                }
            }
        }
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

    private suspend inline fun saveCocktail() = cocktailsRepo.addCocktailToDb(
        cocktailDTO = CocktailDTO(
            id = currState.id,
            name = currState.name,
            description = currState.description,
            recipe = currState.recipe,
            ingredients = currState.ingredients
        )
    )

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
        val cocktail =
            if (initialId != CREATE_NEW_COCKTAIL_ID) cocktailsRepo.loadCocktailByIdFromDB(id)
            else flowOf(
                Resource.Success(
                    CocktailDTO(
                        id = id
                    )
                )
            )
        cocktail.collect {
            Log.d("TAGTAGTAG", "collecting $it")
        }
        screenState.emit(
            EditCocktailScreenState(
                isLoading = true
            )
        )
        cocktail.collect {
            if (it is Resource.Success) {
                val newCocktail = it.data
                screenState.emit(
                    currState.copy(
                        id = id,
                        name = newCocktail.name ?: "",
                        description = newCocktail.description ?: "",
                        recipe = newCocktail.recipe ?: "",
                        ingredients = newCocktail.ingredients ?: emptyArray(),
                        isLoading = false,
                        error = null,
                        errorState = false,
                    )
                )
                Log.d("TAGTAGTAG", "currState is $currState")
            }
        }
    }
}