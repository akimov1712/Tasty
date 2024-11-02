package ru.topbun.detail_recipe.di

import org.koin.dsl.module
import ru.topbun.detail_recipe.DetailRecipeScreen
import ru.topbun.detail_recipe.DetailRecipeViewModel
import ru.topbun.domain.entity.recipe.RecipeEntity

val detailRecipeModule = module {
    factory { DetailRecipeViewModel() }
}