package ru.topbun.data.source.local.database.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.topbun.domain.entity.recipe.DifficultyType

@Entity(tableName = "recipes")
data class RecipeDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: Int?,
    val title: String,
    val description: String?,
    val image: String?,
    val isFavorite: Boolean,
    val categories: List<CategoryDBO>,
    val timeCooking: Int?,
    val difficulty: DifficultyType?,
    val carbs: Int?,
    val fat: Int?,
    val protein: Int?,
    val kcal: Int?,
    val ingredients: List<IngredientsDBO>,
    val steps: List<StepDBO>,
)
