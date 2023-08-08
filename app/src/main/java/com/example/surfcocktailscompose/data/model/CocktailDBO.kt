package com.example.surfcocktailscompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.surfcocktailscompose.util.CocktailConverter

@Entity(
    tableName = "CocktailDatabase"
)
@TypeConverters(CocktailConverter::class)
data class CocktailDBO(
    @PrimaryKey
    val id: String,
    val name: String = "",
    val description: String = "",
    val recipe: String = "",
    val ingredients: Array<String>
)
