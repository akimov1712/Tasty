package ru.topbun.tasty.di

import org.koin.dsl.module
import ru.topbun.auth.fragments.login.loginModule
import ru.topbun.auth.fragments.signUp.signUpModule
import ru.topbun.category.di.categoryModule
import ru.topbun.detail_recipe.di.detailRecipeModule
import ru.topbun.favorite.di.favoriteModule
import ru.topbun.recipes.di.recipeByCategoryModule
import ru.topbun.recipes.di.recipeModule

val featuresModule = module{
    includes(recipeModule, categoryModule, detailRecipeModule, favoriteModule, loginModule, signUpModule, recipeByCategoryModule)
}