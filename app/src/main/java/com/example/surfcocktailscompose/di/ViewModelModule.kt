package com.example.surfcocktailscompose.di

import androidx.lifecycle.ViewModel
import com.example.surfcocktailscompose.presentation.screens.detailsscreen.DetailsViewModel
import com.example.surfcocktailscompose.presentation.screens.homescreen.HomeViewModel
import com.example.surfcocktailscompose.presentation.screens.editcocktailscreen.EditCocktailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun detailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditCocktailViewModel::class)
    fun createCocktailViewModel(viewModel: EditCocktailViewModel): ViewModel
}