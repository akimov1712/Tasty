package ru.topbun.tabs

import cafe.adriel.voyager.core.registry.screenModule
import ru.topbun.add_recipe.AddRecipeScreen
import ru.topbun.category.CategoryScreen
import ru.topbun.favorite.FavoriteScreen
import ru.topbun.navigation.TabScreenProvider
import ru.topbun.profile.ProfileScreen
import ru.topbun.recipes.RecipesScreen

val tabsScreenModule = screenModule {
    register<TabScreenProvider.Category> {
        CategoryScreen
    }
    register<TabScreenProvider.AddRecipe> {
        AddRecipeScreen
    }
    register<TabScreenProvider.Favorite> {
        FavoriteScreen
    }
    register<TabScreenProvider.Profile> {
        ProfileScreen
    }
}
