package ru.topbun.add_recipe

import android.net.Uri
import ru.topbun.common.calculateKcal
import ru.topbun.domain.entity.recipe.DifficultyType
import ru.topbun.domain.entity.recipe.IngredientsEntity
import ru.topbun.domain.entity.recipe.StepEntity

data class AddRecipeState(
    val imageUri: Uri? = null,
    val imageIsError: Boolean = false,
    val title: String = "",
    val titleIsError: Boolean = false,
    val description: String = "",
    val cookingTime: Int? = null,
    val protein: String? = null,
    val proteinIsError: Boolean = false,
    val carbs: String? = null,
    val carbsIsError: Boolean = false,
    val fat: String? = null,
    val fatIsError: Boolean = false,
    val kcalIsError: Boolean = false,
    val difficulty: DifficultyType? = null,
    val ingredients: List<IngredientsEntity> = emptyList(),
    val steps: List<StepEntity> = emptyList(),
    val screenState: AddRecipeScreenState = AddRecipeScreenState.Initial
){

    sealed interface AddRecipeScreenState{
        data object Initial: AddRecipeScreenState
        data object Loading: AddRecipeScreenState
        data object Success: AddRecipeScreenState
        data class Error(val msg: String): AddRecipeScreenState
    }

    fun getKcal() = calculateKcal(protein?.toFloatOrNull(), carbs?.toFloatOrNull(), fat?.toFloatOrNull())
}
