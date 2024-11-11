package ru.topbun.detail_recipe

import android.util.Log
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.registry.screenModule
import kotlinx.coroutines.launch
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.detail_recipe.DetailRecipeState.DetailRecipeScreenState
import ru.topbun.detail_recipe.DetailRecipeState.DetailRecipeScreenState.*
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.useCases.auth.GetAccountInfoUseCase
import ru.topbun.domain.useCases.recipe.ChangeFavoriteUseCase
import ru.topbun.domain.useCases.recipe.DeleteRecipeUseCase
import ru.topbun.domain.useCases.recipe.GetRecipeWithIdUseCase

class DetailRecipeViewModel(
    private val recipesId: Int,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase,
    private val getRecipeWithIdUseCase: GetRecipeWithIdUseCase,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
): ScreenModelState<DetailRecipeState>(DetailRecipeState()) {

    fun changeTabIndex(index: Int) = updateState { copy(selectedTabIndex = index) }

    init {
        loadRecipe()
    }

    fun deleteRecipe(id: Int) = screenModelScope.launch {
        wrapperException({
            deleteRecipeUseCase(id)
            updateState { copy(
                deleteState = DetailRecipeState.DeleteRecipeState.Success,
                showModalDeleteRecipe = false,
                showButtonDeleteRecipe = false
            ) }
        }){ _, msg -> }
    }

    fun loadRecipe() = screenModelScope.launch {
        wrapperException({
            updateState { copy(screenState = Loading) }
            val recipe = getRecipeWithIdUseCase(recipesId)
            updateState { copy(screenState = Success(recipe)) }
            recipe.userId?.let { changeVisibleDeleteButton(it) }
        }){ _, msg ->
            updateState { copy(screenState = Error(msg)) }
        }
    }

    private fun changeVisibleDeleteButton(userId: Int) = screenModelScope.launch {
        wrapperException({
            val userInfo = getAccountInfoUseCase()
            updateState { copy(showButtonDeleteRecipe = userInfo.id == userId) }
        }) {_,_ -> }
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


    fun showCategoriesModal() = updateState { copy(showModalCategories = true) }
    fun hideCategoriesModal() = updateState { copy(showModalCategories = false) }

    fun showDeleteRecipeModal() = updateState { copy(showModalDeleteRecipe = true) }
    fun hideDeleteRecipeModal() = updateState { copy(showModalDeleteRecipe = false) }

}