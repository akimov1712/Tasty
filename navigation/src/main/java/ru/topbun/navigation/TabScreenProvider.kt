package ru.topbun.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class TabScreenProvider : ScreenProvider {
    data object Recipe : TabScreenProvider()
    data object Category : TabScreenProvider()
    data object AddRecipe : TabScreenProvider()
    data object Favorite : TabScreenProvider()
    data object Profile : TabScreenProvider()
}