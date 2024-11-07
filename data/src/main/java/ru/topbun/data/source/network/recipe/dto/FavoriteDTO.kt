package ru.topbun.data.source.network.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteDTO(
    val recipeId: Int,
    val isFavorite: Boolean
)

