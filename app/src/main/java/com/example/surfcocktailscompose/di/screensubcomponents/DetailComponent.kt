package com.example.surfcocktailscompose.di.screensubcomponents

import com.example.surfcocktailscompose.di.DataModule
import com.example.surfcocktailscompose.di.DatabaseModule
import com.example.surfcocktailscompose.presentation.screens.detailsscreen.DetailsViewModel
import dagger.Component
import dagger.Subcomponent

@Details
@Subcomponent
interface DetailComponent {

    val detailViewModel: DetailsViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailComponent
    }
}