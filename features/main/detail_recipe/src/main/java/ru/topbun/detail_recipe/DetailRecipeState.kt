package ru.topbun.detail_recipe

import ru.topbun.detail_recipe.tabs.DetailRecipeTabs

data class DetailRecipeState(
    val tabs:List<DetailRecipeTabs> = DetailRecipeTabs.entries,
    val selectedTabIndex: Int = 0
)
