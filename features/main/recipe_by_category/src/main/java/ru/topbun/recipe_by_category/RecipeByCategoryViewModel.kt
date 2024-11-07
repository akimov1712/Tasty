package ru.topbun.recipe_by_category

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.domain.useCases.recipe.GetRecipeWithCategoryUseCase
import ru.topbun.recipe_by_category.RecipeByCategoryState.RecipeScreenState.Error
import ru.topbun.recipe_by_category.RecipeByCategoryState.RecipeScreenState.Loading
import ru.topbun.recipe_by_category.RecipeByCategoryState.RecipeScreenState.Success

class RecipeByCategoryViewModel(
    private val categoryId: Int,
    private val getRecipeUseCase: GetRecipeWithCategoryUseCase,
): ScreenModelState<RecipeByCategoryState>(RecipeByCategoryState()) {

    private var searchJob: Job? = null


    fun loadRecipes(){
        searchJob?.cancel()
        searchJob = screenModelScope.launch(){
            wrapperException({
                updateState { copy(recipeState = Loading) }
                val result = getRecipeUseCase(
                    categoryId = categoryId,
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

}