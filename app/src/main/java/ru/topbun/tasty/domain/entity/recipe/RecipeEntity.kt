package ru.topbun.tasty.domain.entity.recipe

data class RecipeEntity(
    val id: Int,
    val userId: Int?,
    val title: String,
    val description: String?,
    val image: String?,
    val categoryId: List<Int>,
    val timeCooking: Int?,
    val difficulty: DifficultyType,
    val carbs: Int?,
    val fat: Int?,
    val protein: Int?,
    val kcal: Int?,
    val ingredients: List<IngredientsEntity>,
    val steps: List<StepEntity>,
)
