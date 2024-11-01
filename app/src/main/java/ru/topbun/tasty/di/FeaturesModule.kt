package ru.topbun.tasty.di

import org.koin.dsl.module
import ru.topbun.category.di.categoryModule
import ru.topbun.recipes.di.recipeModule

val featuresModule = module{
    includes(recipeModule, categoryModule)
}