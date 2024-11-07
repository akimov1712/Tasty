package ru.topbun.detail_recipe

import android.util.Log
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.useCases.recipe.ChangeFavoriteUseCase

class DetailRecipeViewModel(
    private val recipes: RecipeEntity,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase
): ScreenModelState<DetailRecipeState>(DetailRecipeState(recipes)) {

    fun changeTabIndex(index: Int) = updateState { copy(selectedTabIndex = index) }

    fun changeFavorite() = screenModelScope.launch {
        wrapperException({
            println(stateValue.recipe)
            val favorite = changeFavoriteUseCase(stateValue.recipe.id, !stateValue.recipe.isFavorite)
            updateState { copy(recipe = recipe.copy(isFavorite = favorite)) }
            println(stateValue.recipe)
        }){ _, msg ->
            updateState { copy(errorMessage = msg) }
        }
    }

}