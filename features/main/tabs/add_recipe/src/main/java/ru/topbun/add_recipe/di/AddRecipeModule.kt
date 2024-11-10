package ru.topbun.add_recipe.di

import org.koin.dsl.module
import ru.topbun.add_recipe.AddRecipeViewModel

val addRecipeModule = module {
    single { AddRecipeViewModel() }
}