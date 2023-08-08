package com.example.surfcocktailscompose.di.screensubcomponents

import com.example.surfcocktailscompose.presentation.screens.editcocktailscreen.EditCocktailViewModel
import dagger.Subcomponent

@CreateCocktail
@Subcomponent
interface EditCocktailComponent {

    val viewModel: EditCocktailViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(): EditCocktailComponent
    }
}