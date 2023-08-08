package com.example.surfcocktailscompose.util

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CocktailConverter {

    @TypeConverter
    fun fromList(value : Array<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<Array<String>>(value)

}