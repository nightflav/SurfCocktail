package com.example.surfcocktailscompose.data.model

data class CocktailDTO(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val recipe: String? = null,
    val ingredients: Array<String>? = null
)
