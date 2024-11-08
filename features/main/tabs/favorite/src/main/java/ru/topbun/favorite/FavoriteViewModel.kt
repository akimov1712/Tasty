package ru.topbun.favorite

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.domain.useCases.recipe.ChangeFavoriteUseCase

class FavoriteViewModel(
    private val changeFavoriteUseCase: ChangeFavoriteUseCase
): ScreenModelState<FavoriteState>(FavoriteState()) {

    fun changeFavorite(recipeId: Int, value: Boolean) = screenModelScope.launch {
        wrapperException({
            val favorite = changeFavoriteUseCase(recipeId, value)
            updateState {
                val newRecipes = recipes.toMutableList()
                newRecipes.replaceAll {
                    if (it.id == recipeId)it.copy(isFavorite = favorite)
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