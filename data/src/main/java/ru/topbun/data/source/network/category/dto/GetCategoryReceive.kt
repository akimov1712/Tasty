package ru.topbun.data.source.network.category.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCategoryReceive(
    @SerialName("q") val q: String,
    @SerialName("offset") val offset: Int,
    @SerialName("limit") val limit: Int
)


