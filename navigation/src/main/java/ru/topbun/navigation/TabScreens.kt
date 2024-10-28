package ru.topbun.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class TabScreens : ScreenProvider {
    data object Recipe : TabScreens()
    data object Category : TabScreens()
    data object AddRecipe : TabScreens()
    data object Favorite : TabScreens()
    data object Profile : TabScreens()
}