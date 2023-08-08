package com.example.surfcocktailscompose.di

import android.content.Context
import androidx.room.Room
import com.example.surfcocktailscompose.data.database.CocktailsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCocktailsDatabase(context: Context): CocktailsDatabase {
        return Room.databaseBuilder(
            context,
            CocktailsDatabase::class.java,
            "CocktailsDB"
        ).build()
    }

    @Provides
    fun provideCocktailsDao(database: CocktailsDatabase) = database.dao
}