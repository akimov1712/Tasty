package ru.topbun.detail_recipe

import ru.topbun.detail_recipe.tabs.DetailRecipeTabs
import ru.topbun.domain.entity.recipe.RecipeEntity

data class DetailRecipeState(
    val recipe: RecipeEntity,
    val tabs:List<DetailRecipeTabs> = DetailRecipeTabs.entries,
    val selectedTabIndex: Int = 0,
    val errorMessage: String? = null
)
