package ru.topbun.data.source.network.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteReceive(
    val isFavorite: Boolean
)
