package com.example.surfcocktailscompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.surfcocktailscompose.data.model.CocktailDBO
import com.example.surfcocktailscompose.util.CocktailConverter

@Database(
    version = 1,
    entities = [CocktailDBO::class]
)
@TypeConverters(CocktailConverter::class)
abstract class CocktailsDatabase : RoomDatabase() {
    abstract val dao: CocktailDao
}