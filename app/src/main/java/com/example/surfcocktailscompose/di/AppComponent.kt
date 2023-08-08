package com.example.surfcocktailscompose.di

import android.content.Context
import com.example.surfcocktailscompose.di.screensubcomponents.EditCocktailComponent
import com.example.surfcocktailscompose.di.screensubcomponents.DetailComponent
import com.example.surfcocktailscompose.di.screensubcomponents.HomeComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DatabaseModule::class])
interface AppComponent {

    fun detailComponent(): DetailComponent.Factory

    fun homeComponent(): HomeComponent.Factory

    fun editCocktailComponent(): EditCocktailComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context
        ): AppComponent
    }
}