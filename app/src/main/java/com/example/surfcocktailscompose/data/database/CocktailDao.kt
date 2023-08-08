package com.example.surfcocktailscompose.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.surfcocktailscompose.data.model.CocktailDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Insert (
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertCocktail(cocktail: CocktailDBO)

    @Delete
    suspend fun deleteCocktail(cocktail: CocktailDBO)

    @Query("SELECT * FROM CocktailDatabase ORDER BY name DESC")
    fun getAllCocktails(): Flow<List<CocktailDBO>?>

    @Query("SELECT * FROM CocktailDatabase WHERE id=:id")
    fun getCocktailById(id: String): Flow<CocktailDBO?>
}