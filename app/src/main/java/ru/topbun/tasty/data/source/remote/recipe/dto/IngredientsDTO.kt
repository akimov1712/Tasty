package ru.topbun.tasty.data.source.remote.recipe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientsDTO(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("value") val value: String,
)
