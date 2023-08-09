package com.example.surfcocktailscompose.presentation.screens.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfcocktailscompose.data.repository.CocktailsRepository
import com.example.surfcocktailscompose.di.screensubcomponents.Home
import com.example.surfcocktailscompose.util.BaseReducer
import com.example.surfcocktailscompose.util.BaseViewModel
import com.example.surfcocktailscompose.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val cocktailRepo: CocktailsRepository
) : BaseViewModel<HomeScreenState, UserHomeIntents>() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    override val state: Flow<HomeScreenState> = _homeScreenState.asStateFlow()
    private val currState = _homeScreenState.asStateFlow().value

    fun sendEvent(event: UserHomeIntents) {
        when(event) {
            UserHomeIntents.CreateNewCocktail -> {}
            is UserHomeIntents.SelectExistingCocktail -> {}
            UserHomeIntents.DeleteAllCocktails -> {
                viewModelScope.launch {
                    cocktailRepo.deleteAllCocktails()
                    cocktailRepo.loadCocktailsFromDB()
                        .collect { _homeScreenState.emit(
                            when(it) {
                                is Resource.Error -> {
                                    currState.copy(
                                        isLoading = false,
                                        cocktails = emptyList(),
                                        errorState = true,
                                        error = it.error as Exception
                                    )
                                }
                                is Resource.Loading -> {
                                    currState.copy(
                                        isLoading = true,
                                        cocktails = emptyList(),
                                        errorState = false
                                    )
                                }
                                is Resource.Success -> {
                                    currState.copy(
                                        cocktails = it.data,
                                        isLoading = false,
                                        errorState = false
                                    )
                                }
                            }
                        ) }
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            _homeScreenState.emit(
                HomeScreenState(isLoading = true)
            )
            cocktailRepo.loadCocktailsFromDB()
                .collect { _homeScreenState.emit(
                    when(it) {
                        is Resource.Error -> {
                            currState.copy(
                                isLoading = false,
                                cocktails = emptyList(),
                                errorState = true,
                                error = it.error as Exception
                            )
                        }
                        is Resource.Loading -> {
                            currState.copy(
                                isLoading = true,
                                cocktails = emptyList(),
                                errorState = false
                            )
                        }
                        is Resource.Success -> {
                            currState.copy(
                                cocktails = it.data,
                                isLoading = false,
                                errorState = false
                            )
                        }
                    }
                ) }
        }
    }

}