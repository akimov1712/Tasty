package ru.topbun.tasty_purple.di

import org.koin.dsl.module
import ru.topbun.add_recipe.di.addRecipeModule
import ru.topbun.auth.fragments.login.loginModule
import ru.topbun.auth.fragments.signUp.signUpModule
import ru.topbun.category.di.categoryModule
import ru.topbun.detail_recipe.di.detailRecipeModule
import ru.topbun.favorite.di.favoriteModule
import ru.topbun.recipe_by_category.di.recipeByCategoryModule
import ru.topbun.recipes.di.profileModule
import ru.topbun.recipes.di.recipeModule

val featuresModule = module{
    includes(recipeModule, addRecipeModule, categoryModule, detailRecipeModule, favoriteModule, loginModule, signUpModule, recipeByCategoryModule, profileModule)
}