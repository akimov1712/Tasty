package ru.topbun.recipes

import androidx.compose.foundation.lazy.LazyListState
import ru.topbun.domain.entity.recipe.RecipeEntity

data class RecipeState(
    val searchQuery: String = "",
    val tabs: List<RecipeTabs> = RecipeTabs.entries,
    val selectedTabIndex: Int = 0,
    val lazyListState: LazyListState = LazyListState(),
    val recipes: List<RecipeEntity> = listOf(),
    val isEndList: Boolean = false,
    val recipeState: RecipeScreenState = RecipeScreenState.Initial
){

    sealed interface RecipeScreenState{
        data object Initial: RecipeScreenState
        data object Loading: RecipeScreenState
        data class Error(val msg: String): RecipeScreenState
        data object Success: RecipeScreenState
    }

}
