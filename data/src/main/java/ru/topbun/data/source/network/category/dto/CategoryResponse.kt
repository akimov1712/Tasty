package ru.topbun.data.source.network.category.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.tasty.data.source.remote.category.dto.CategoryDTO

@Serializable
data class CategoryResponse(
    @SerialName("categories") val categories: List<CategoryDTO>
)
