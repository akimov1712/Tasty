package ru.topbun.recipe_by_category

import androidx.compose.foundation.lazy.grid.LazyGridState
import ru.topbun.domain.entity.recipe.RecipeEntity

data class RecipeByCategoryState(
    val isEndList: Boolean = false,
    val recipes: List<RecipeEntity> = emptyList(),
    val recipeState: RecipeScreenState = RecipeScreenState.Initial,
    val favoriteState: FavoriteScreenState = FavoriteScreenState.Initial
){

    sealed interface FavoriteScreenState{
        data object Initial: FavoriteScreenState
        data class Error(val msg: String): FavoriteScreenState
        data object Success: FavoriteScreenState
    }


    sealed interface RecipeScreenState{
        data object Initial: RecipeScreenState
        data object Loading: RecipeScreenState
        data class Error(val msg: String): RecipeScreenState
        data object Success: RecipeScreenState
    }

}
