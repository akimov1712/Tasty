package ru.topbun.navigation.main

import cafe.adriel.voyager.core.registry.ScreenProvider
import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.domain.entity.recipe.RecipeEntity

sealed class MainScreenProvider : ScreenProvider {
    data object Tabs : MainScreenProvider()
    data class DetailRecipe(val recipeId: Int, val fromCache: Boolean) : MainScreenProvider()
    data object Auth : MainScreenProvider()
    data class RecipeByCategory(val category: CategoryEntity) : MainScreenProvider()
}