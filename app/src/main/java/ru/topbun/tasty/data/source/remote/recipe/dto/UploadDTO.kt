package ru.topbun.tasty.data.source.remote.recipe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadDTO(
    @SerialName("url") val url: String
)
