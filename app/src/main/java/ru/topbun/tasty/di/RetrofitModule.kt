package ru.topbun.tasty.di

import org.koin.dsl.module
import ru.topbun.tasty.data.source.remote.ApiFactory
import ru.topbun.tasty.data.source.remote.auth.AuthApi
import ru.topbun.tasty.data.source.remote.category.CategoryApi
import ru.topbun.tasty.data.source.remote.recipe.RecipeApi

val retrofitModule = module {
    single<RecipeApi>{ ApiFactory.recipeApi }
    single<CategoryApi>{ ApiFactory.categoryApi }
    single<AuthApi>{ ApiFactory.authApi }
}