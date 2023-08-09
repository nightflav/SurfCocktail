package com.example.surfcocktailscompose.data.repository

import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.util.Resource
import kotlinx.coroutines.flow.Flow

interface CocktailsRepository {

    fun loadCocktailsFromDB(): Flow<Resource<List<CocktailDTO>>>

    fun loadCocktailByIdFromDB(id: String): Flow<Resource<CocktailDTO>>

    suspend fun addCocktailToDb(cocktailDTO: CocktailDTO)

    suspend fun deleteAllCocktails()
}