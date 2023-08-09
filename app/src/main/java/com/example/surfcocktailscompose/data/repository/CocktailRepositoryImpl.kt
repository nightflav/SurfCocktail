package com.example.surfcocktailscompose.data.repository

import android.util.Log
import com.example.surfcocktailscompose.data.database.CocktailDao
import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.data.repository.Errors.NoSuchElementInDatabaseException
import com.example.surfcocktailscompose.util.Resource
import com.example.surfcocktailscompose.util.toCocktailDBO
import com.example.surfcocktailscompose.util.toCocktailDTO
import com.example.surfcocktailscompose.util.toCocktailDTOList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CocktailRepositoryImpl(
    private val cocktailsDao: CocktailDao
) : CocktailsRepository {

    override fun loadCocktailsFromDB(): Flow<Resource<List<CocktailDTO>>> {
        return flow {
            emit(Resource.Loading())
            val cocktails = cocktailsDao.getAllCocktails()
                .map { list ->
                    if (list == null)
                        Resource.Error<List<CocktailDTO>>(
                            NoSuchElementInDatabaseException,
                            null
                        )
                    else
                        Resource.Success(list.toCocktailDTOList())
                }
            emitAll(cocktails)
        }
    }

    override fun loadCocktailByIdFromDB(id: String): Flow<Resource<CocktailDTO>> {
        return flow {
            emit(Resource.Loading())
            val cocktail = cocktailsDao.getCocktailById(id = id).map {
                if (it == null)
                    Resource.Error<CocktailDTO>(
                        NoSuchElementInDatabaseException,
                        null
                    )
                else
                    Resource.Success(
                        it.toCocktailDTO()
                    )
            }
            emitAll(cocktail)
        }
    }

    override suspend fun addCocktailToDb(cocktailDTO: CocktailDTO) {
        cocktailsDao.insertCocktail(cocktailDTO.toCocktailDBO())
    }

    override suspend fun deleteAllCocktails() {
        cocktailsDao.clearDatabase()
    }
}

private object Errors {
    val NoSuchElementInDatabaseException = Exception("No Such Element In Database")
}