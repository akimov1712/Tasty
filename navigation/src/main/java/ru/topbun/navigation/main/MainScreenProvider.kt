package ru.topbun.navigation.main

import cafe.adriel.voyager.core.registry.ScreenProvider
import ru.topbun.domain.entity.recipe.RecipeEntity

sealed class MainScreenProvider : ScreenProvider {
    data object Tabs : MainScreenProvider()
    data class DetailRecipe(val recipe: RecipeEntity) : MainScreenProvider()
    data object Auth : MainScreenProvider()
}