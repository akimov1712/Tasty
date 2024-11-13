package ru.topbun.favorite

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.domain.useCases.recipe.ChangeFavoriteUseCase
import ru.topbun.domain.useCases.recipe.GetFavoritesRecipeUseCase

class FavoriteViewModel(
    private val changeFavoriteUseCase: ChangeFavoriteUseCase,
    private val getFavoritesRecipeUseCase: GetFavoritesRecipeUseCase,
): ScreenModelState<FavoriteState>(FavoriteState()) {

    fun loadRecipes() = screenModelScope.launch {
        wrapperException({
            updateState { copy(recipeState = FavoriteState.RecipeScreenState.Loading) }
            val recipe = getFavoritesRecipeUseCase()
            updateState { copy(recipes = recipe, recipeState = FavoriteState.RecipeScreenState.Success) }
        }){ _, msg ->
            updateState { copy(recipeState = FavoriteState.RecipeScreenState.Error(msg)) }
        }
    }

    fun changeFavorite(recipeId: Int, value: Boolean) = screenModelScope.launch {
        wrapperException({
            val favorite = changeFavoriteUseCase(recipeId, value)
            updateState {
                val newRecipes = recipes.toMutableList()
                newRecipes.replaceAll {
                    if (it.id == recipeId) it.copy(isFavorite = favorite)
                    else it
                }
                copy(recipes = newRecipes)
            }
            updateState { copy(favoriteState = FavoriteState.FavoriteScreenState.Success) }
        }){ _, msg ->
            updateState { copy(favoriteState = FavoriteState.FavoriteScreenState.Error(msg)) }
        }
    }

}