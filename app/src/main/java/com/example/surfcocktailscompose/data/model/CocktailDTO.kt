package com.example.surfcocktailscompose.data.model

data class CocktailDTO(
    val id: String,
    val name: String = "",
    val description: String = "",
    val recipe: String = "",
    val ingredients: Array<String> = emptyArray()
)
