package ru.topbun.data.di

import org.koin.dsl.module
import ru.topbun.tasty.data.source.remote.ApiFactory
import ru.topbun.tasty.data.source.remote.auth.AuthApi
import ru.topbun.tasty.data.source.remote.category.CategoryApi
import ru.topbun.tasty.data.source.remote.recipe.RecipeApi

val retrofitModule = module {
    single<ApiFactory> { ApiFactory(get()) }
    single<RecipeApi> { get<ApiFactory>().recipeApi }
    single<CategoryApi> { get<ApiFactory>().categoryApi }
    single<AuthApi> { get<ApiFactory>().authApi }
}