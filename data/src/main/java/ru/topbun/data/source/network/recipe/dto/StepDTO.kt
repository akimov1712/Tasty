package ru.topbun.tasty.data.source.remote.recipe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StepDTO(
    @SerialName("id") val id: Int,
    @SerialName("text") val text: String,
    @SerialName("preview") val preview: String?,
    @SerialName("order") val order: Int,
)
