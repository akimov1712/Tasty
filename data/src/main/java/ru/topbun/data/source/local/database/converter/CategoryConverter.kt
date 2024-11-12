package ru.topbun.data.source.local.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.topbun.data.source.local.database.dbo.CategoryDBO

class CategoryConverter {

    @TypeConverter
    fun toString(list: List<CategoryDBO>): String = Json.encodeToString(list)

    @TypeConverter
    fun fromString(str: String): List<CategoryDBO> = Json.decodeFromString(str)

}