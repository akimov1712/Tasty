package ru.topbun.data.source.local.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.topbun.data.source.local.database.dbo.CategoryDBO
import ru.topbun.data.source.local.database.dbo.StepDBO

class StepConverter {
    @TypeConverter
    fun toString(list: List<StepDBO>): String = Json.encodeToString(list)
    @TypeConverter
    fun fromString(str: String): List<StepDBO> = Json.decodeFromString(str)

}