package ru.topbun.tasty.data.source.remote.recipe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.domain.entity.recipe.DifficultyType

@Serializable
data class RecipeDTO(
    @SerialName("id") val id: Int,
    @SerialName("userId") val userId: Int?,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String?,
    @SerialName("image") val image: String?,
    @SerialName("categoryId") val categoryId: List<Int>,
    @SerialName("timeCooking") val timeCooking: Int?,
    @SerialName("difficulty") val difficulty: DifficultyType,
    @SerialName("carbs") val carbs: Int?,
    @SerialName("fat") val fat: Int?,
    @SerialName("protein") val protein: Int?,
    @SerialName("kcal") val kcal: Int?,
    @SerialName("ingredients") val ingredients: List<IngredientsDTO>,
    @SerialName("steps") val steps: List<StepDTO>,
)
