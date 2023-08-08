package com.example.surfcocktailscompose.di.screensubcomponents

import com.example.surfcocktailscompose.di.DataModule
import com.example.surfcocktailscompose.di.DatabaseModule
import com.example.surfcocktailscompose.presentation.screens.homescreen.HomeViewModel
import dagger.Component
import dagger.Subcomponent

@Home
@Subcomponent
interface HomeComponent {

    val viewModel: HomeViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }
}