package ru.topbun.data.source.local.database.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "ingredients")
@Serializable
data class IngredientsDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val value: String,
)
