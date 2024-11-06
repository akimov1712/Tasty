package ru.topbun.recipe_by_category

import androidx.compose.foundation.lazy.grid.LazyGridState
import ru.topbun.domain.entity.recipe.RecipeEntity

data class RecipeByCategoryState(
    val isEndList: Boolean = false,
    val recipes: List<RecipeEntity> = emptyList(),
    val recipeState: RecipeScreenState = RecipeScreenState.Initial
){

    sealed interface RecipeScreenState{
        data object Initial: RecipeScreenState
        data object Loading: RecipeScreenState
        data class Error(val msg: String): RecipeScreenState
        data object Success: RecipeScreenState
    }

}
