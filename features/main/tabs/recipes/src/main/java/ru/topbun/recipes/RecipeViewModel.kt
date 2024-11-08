package ru.topbun.recipes

import android.util.Log
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.internal.platform.android.AndroidLogHandler.publish
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.android.handlerTokenException
import ru.topbun.domain.useCases.recipe.ChangeFavoriteUseCase

import ru.topbun.domain.useCases.recipe.GetRecipeUseCase
import ru.topbun.recipes.RecipeState.RecipeScreenState.*

class RecipeViewModel(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase
): ScreenModelState<RecipeState>(RecipeState()) {

    private var searchJob: Job? = null

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
            updateState { copy(favoriteState = RecipeState.FavoriteScreenState.Success) }
        }){ _, msg ->
            updateState { copy(favoriteState = RecipeState.FavoriteScreenState.Error(msg)) }
        }
    }

    fun loadRecipes(){
        searchJob?.cancel()
        searchJob = screenModelScope.launch(){
            wrapperException({
                updateState { copy(recipeState = Loading) }
                val result = getRecipeUseCase(
                    q = stateValue.searchQuery,
                    isMyRecipe = stateValue.tabs[stateValue.selectedTabIndex] == RecipeTabs.MyRecipes,
                    offset = stateValue.recipes.size,
                )
                if (result.isEmpty()) updateState { copy(isEndList = true) }
                updateState { copy(recipes = recipes + result) }
                updateState { copy(recipeState = Success) }
            }) { _, msg ->
                updateState { copy(recipeState = Error(msg)) }
            }
        }
    }

    fun changeQuery(q: String){
        clearRecipes()
        updateState { copy(searchQuery = q) }
    }

    fun changeTab(index: Int) {
        clearRecipes()
        updateState { copy(selectedTabIndex = index) }
    }

    private fun clearRecipes() = updateState {
        copy(
            recipes = emptyList(),
            isEndList = false,
            recipeState = Initial
        )
    }

}