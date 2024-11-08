package ru.topbun.favorite

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import ru.topbun.domain.entity.recipe.RecipeEntity

data class FavoriteState(
    val lazyListState: LazyGridState = LazyGridState(),
    val recipes: List<RecipeEntity> = listOf(),
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
