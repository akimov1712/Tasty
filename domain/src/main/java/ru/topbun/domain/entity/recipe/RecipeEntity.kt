package ru.topbun.domain.entity.recipe

import ru.topbun.domain.entity.category.CategoryEntity

data class RecipeEntity(
    val id: Int,
    val userId: Int?,
    val title: String,
    val description: String?,
    val image: String?,
    val isFavorite: Boolean,
    val categories: List<CategoryEntity>,
    val timeCooking: Int?,
    val difficulty: DifficultyType,
    val carbs: Int?,
    val fat: Int?,
    val protein: Int?,
    val kcal: Int?,
    val ingredients: List<IngredientsEntity>,
    val steps: List<StepEntity>,
){

    fun getSumNutrients() = (carbs ?: 0) + (fat ?: 0) + (protein ?: 0)

}
