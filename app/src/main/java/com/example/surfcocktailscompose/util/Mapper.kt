package com.example.surfcocktailscompose.util

import com.example.surfcocktailscompose.data.model.CocktailDBO
import com.example.surfcocktailscompose.data.model.CocktailDTO

fun CocktailDBO.toCocktailDTO() = CocktailDTO(
    id = this.id,
    name = this.name,
    description = this.description,
    ingredients = this.ingredients,
    recipe = this.recipe,
)

fun CocktailDTO.toCocktailDBO() = CocktailDBO(
    id = this.id,
    name = this.name ?: "",
    description = this.description ?: "",
    ingredients = this.ingredients ?: emptyArray(),
    recipe = this.recipe ?: "",
)

fun Iterable<CocktailDBO>.toCocktailDTOList() = this.map { it.toCocktailDTO() }