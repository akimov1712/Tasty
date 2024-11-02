package ru.topbun.tasty.di

import org.koin.dsl.module
import ru.topbun.category.di.categoryModule
import ru.topbun.detail_recipe.di.detailRecipeModule
import ru.topbun.main.di.mainModule
import ru.topbun.recipes.di.recipeModule

val featuresModule = module{
    includes(mainModule, recipeModule, categoryModule, detailRecipeModule)
}