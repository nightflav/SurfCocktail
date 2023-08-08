package com.example.surfcocktailscompose.di

import com.example.surfcocktailscompose.data.database.CocktailDao
import com.example.surfcocktailscompose.data.repository.CocktailRepositoryImpl
import com.example.surfcocktailscompose.data.repository.CocktailsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Singleton
    @Provides
    fun provideCocktailsRepo(
        cocktailsDao: CocktailDao
    ) : CocktailsRepository {
        return CocktailRepositoryImpl(cocktailsDao)
    }
}