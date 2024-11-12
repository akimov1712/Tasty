package ru.topbun.data.source.local.database.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "categories")
@Serializable
data class CategoryDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val image: String
)
