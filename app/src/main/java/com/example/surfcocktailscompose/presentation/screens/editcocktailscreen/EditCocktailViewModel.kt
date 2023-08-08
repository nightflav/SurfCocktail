package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import androidx.lifecycle.ViewModel
import com.example.surfcocktailscompose.data.repository.CocktailsRepository
import javax.inject.Inject

class EditCocktailViewModel @Inject constructor(
    private val cocktailsRepo: CocktailsRepository
) : ViewModel() {
}