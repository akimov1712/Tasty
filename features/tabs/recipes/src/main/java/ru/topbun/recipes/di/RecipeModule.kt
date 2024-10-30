package ru.topbun.recipes.di

import org.koin.dsl.module
import ru.topbun.recipes.RecipeViewModel

val recipeModule = module {
    single { RecipeViewModel(get()) }
}