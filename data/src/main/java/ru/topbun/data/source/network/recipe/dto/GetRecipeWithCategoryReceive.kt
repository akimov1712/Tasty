package ru.topbun.data.source.network.recipe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRecipeWithoutQueryReceive(
    @SerialName("offset") val offset: Int,
    @SerialName("limit") val limit: Int
)