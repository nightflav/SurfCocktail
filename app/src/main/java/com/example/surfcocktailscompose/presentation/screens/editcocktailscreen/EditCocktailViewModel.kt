package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.data.repository.CocktailsRepository
import com.example.surfcocktailscompose.presentation.screens.detailsscreen.DetailsScreenState
import com.example.surfcocktailscompose.util.BaseReducer
import com.example.surfcocktailscompose.util.BaseViewModel
import com.example.surfcocktailscompose.util.Consts.CREATE_NEW_COCKTAIL_ID
import com.example.surfcocktailscompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

class EditCocktailViewModel @Inject constructor(
    private val cocktailsRepo: CocktailsRepository
) : BaseViewModel<EditCocktailScreenState, UserEditCocktailIntents>() {

    private val screenState = MutableStateFlow(EditCocktailScreenState())
    override val state: Flow<EditCocktailScreenState> = screenState.asStateFlow()
    private val currState = screenState.value

    fun sendEvent(event: UserEditCocktailIntents) {
        viewModelScope.launch {
            val cocktail =
                if (event.id != CREATE_NEW_COCKTAIL_ID) cocktailsRepo.loadCocktailByIdFromDB(event.id)
                else flowOf(
                    Resource.Success(
                        CocktailDTO(
                            id = System.currentTimeMillis().hashCode().toString()
                        )
                    )
                )
            when (event) {
                is UserEditCocktailIntents.Init -> {
                    screenState.emit(
                        EditCocktailScreenState(
                            isLoading = true
                        )
                    )
                    cocktail.collect {
                        if (it is Resource.Success) {

                            Log.d("nice", "${it.data}")
                            val newCocktail = it.data
                            screenState.emit(
                                currState.copy(
                                    name = newCocktail.name,
                                    description = newCocktail.description,
                                    recipe = newCocktail.recipe,
                                    ingredients = newCocktail.ingredients,
                                    isLoading = false,
                                    error = null,
                                    errorState = false,
                                )
                            )
                        }
                    }
                }

                is UserEditCocktailIntents.AddNewIngredient -> {
                    screenState.emit(
                        currState.copy(
                            ingredients = currState.ingredients.toMutableList().apply {
                                this.add(event.ingredient)
                            }.toTypedArray()
                        )
                    )
                }

                is UserEditCocktailIntents.EditDescription -> {
                    screenState.emit(
                        currState.copy(
                            description = event.desc
                        )
                    )
                }

                is UserEditCocktailIntents.EditName -> {
                    screenState.emit(
                        currState.copy(
                            description = event.name
                        )
                    )
                }

                is UserEditCocktailIntents.EditRecipe -> {
                    screenState.emit(
                        currState.copy(
                            description = event.recipe
                        )
                    )
                }

                is UserEditCocktailIntents.SaveNewCocktail -> {
                    cocktailsRepo.addCocktailToDb(event.cocktail)
                }

                is UserEditCocktailIntents.OpenDialog -> {
                    screenState.emit(
                        currState.copy(
                            showDialog = true,
                            isLoading = false,
                            errorState = false,
                            error = null
                        )
                    )
                }
            }
        }
    }
}