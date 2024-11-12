package ru.topbun.data.source.local.database.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "step")
@Serializable
data class StepDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val preview: String?,
    val order: Int,
)
