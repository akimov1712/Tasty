package ru.topbun.data.source.local.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.topbun.data.source.local.database.dbo.CategoryDBO
import ru.topbun.data.source.local.database.dbo.IngredientsDBO

class IngredientsConverter {

    @TypeConverter
    fun toString(list: List<IngredientsDBO>): String = Json.encodeToString(list)
    @TypeConverter
    fun fromString(str: String): List<IngredientsDBO> = Json.decodeFromString(str)

}