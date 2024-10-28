package ru.topbun.tabs

import cafe.adriel.voyager.core.registry.screenModule
import ru.topbun.add_recipe.AddRecipeScreen
import ru.topbun.category.CategoryScreen
import ru.topbun.favorite.FavoriteScreen
import ru.topbun.navigation.TabScreens
import ru.topbun.profile.ProfileScreen
import ru.topbun.recipes.RecipesScreen

val tabsScreenModule = screenModule {
    register<TabScreens.Recipe> {
        RecipesScreen
    }
    register<TabScreens.Category> {
        CategoryScreen
    }
    register<TabScreens.AddRecipe> {
        AddRecipeScreen
    }
    register<TabScreens.Favorite> {
        FavoriteScreen
    }
    register<TabScreens.Profile> {
        ProfileScreen
    }
}
