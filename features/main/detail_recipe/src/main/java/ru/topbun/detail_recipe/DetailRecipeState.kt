package ru.topbun.detail_recipe

import ru.topbun.detail_recipe.tabs.DetailRecipeTabs
import ru.topbun.domain.entity.recipe.RecipeEntity

data class DetailRecipeState(
    val tabs:List<DetailRecipeTabs> = DetailRecipeTabs.entries,
    val selectedTabIndex: Int = 0,
    val showModalCategories: Boolean = false,
    val showModalDeleteRecipe: Boolean = false,
    val showButtonDeleteRecipe: Boolean = false,
    val screenState: DetailRecipeScreenState = DetailRecipeScreenState.Loading,
    val deleteState: DeleteRecipeState = DeleteRecipeState.Initial
){

    sealed interface DeleteRecipeState{
        data object Initial: DeleteRecipeState
        data object Success: DeleteRecipeState

    }

    sealed interface DetailRecipeScreenState{
        data object Loading: DetailRecipeScreenState
        data class Error(val msg: String): DetailRecipeScreenState
        data class Success(val recipe: RecipeEntity): DetailRecipeScreenState

    }

}
