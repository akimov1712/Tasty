package ru.topbun.tasty.di

import org.koin.dsl.module
import ru.topbun.recipes.di.recipeModule

val featuresModule = module{
    includes(recipeModule,)
}