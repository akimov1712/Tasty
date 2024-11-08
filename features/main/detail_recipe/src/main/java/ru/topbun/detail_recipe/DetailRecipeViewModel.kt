package ru.topbun.detail_recipe

import android.util.Log
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.detail_recipe.DetailRecipeState.DetailRecipeScreenState
import ru.topbun.detail_recipe.DetailRecipeState.DetailRecipeScreenState.*
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.useCases.recipe.ChangeFavoriteUseCase
import ru.topbun.domain.useCases.recipe.GetRecipeWithIdUseCase

class DetailRecipeViewModel(
    private val recipesId: Int,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase,
    private val getRecipeWithIdUseCase: GetRecipeWithIdUseCase,
): ScreenModelState<DetailRecipeState>(DetailRecipeState()) {

    fun changeTabIndex(index: Int) = updateState { copy(selectedTabIndex = index) }

    init {
        loadRecipe()
    }

    fun loadRecipe() = screenModelScope.launch {
        wrapperException({
            updateState { copy(screenState = Loading) }
            val recipe = getRecipeWithIdUseCase(recipesId)
            updateState { copy(screenState = Success(recipe)) }
        }){ _, msg ->
            updateState { copy(screenState = Error(msg)) }
        }
    }

    fun changeFavorite(value: Boolean) = screenModelScope.launch {
        wrapperException({
            val favorite = changeFavoriteUseCase(recipesId, value)
            updateState {
                val screenState = screenState
                if(screenState is Success){
                    copy(screenState = screenState.copy(screenState.recipe.copy(isFavorite = favorite)))
                } else this
            }

        }){ _, msg ->
            updateState { copy(screenState = Error(msg)) }
        }
    }

}