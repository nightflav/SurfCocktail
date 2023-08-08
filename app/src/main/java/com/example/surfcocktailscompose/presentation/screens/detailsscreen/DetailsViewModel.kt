package com.example.surfcocktailscompose.presentation.screens.detailsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.data.repository.CocktailsRepository
import com.example.surfcocktailscompose.presentation.screens.homescreen.HomeScreenState
import com.example.surfcocktailscompose.presentation.screens.homescreen.UserHomeIntents
import com.example.surfcocktailscompose.util.BaseReducer
import com.example.surfcocktailscompose.util.BaseViewModel
import com.example.surfcocktailscompose.util.Resource
import com.example.surfcocktailscompose.util.UiEvent
import com.example.surfcocktailscompose.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val cocktailRepo: CocktailsRepository,
) : BaseViewModel<DetailsScreenState, UserDetailsIntents>() {

    private val _detailsScreenState = MutableStateFlow(DetailsScreenState())
    override val state: Flow<DetailsScreenState> = _detailsScreenState.asStateFlow()
    private val currState = _detailsScreenState.value

    fun requestForCocktailById(id: String) {
        viewModelScope.launch {
            _detailsScreenState.emit(
                currState.copy(
                    isLoading = true
                )
            )
            cocktailRepo.loadCocktailByIdFromDB(id)
                .collect {
                    when (it) {
                        is Resource.Error -> {
                            _detailsScreenState.emit(
                                currState.copy(
                                    errorState = true,
                                    isLoading = false,
                                    error = it.error
                                )
                            )
                        }

                        is Resource.Loading -> {
                            _detailsScreenState.emit(
                                currState.copy(
                                    isLoading = true,
                                    errorState = false
                                )
                            )
                        }

                        is Resource.Success -> {
                            _detailsScreenState.emit(
                                currState.copy(
                                    isLoading = false,
                                    errorState = false,
                                    cocktail = it.data
                                )
                            )
                        }
                    }
                }
        }
    }
}