package ru.topbun.data.di

import org.koin.dsl.module
import ru.topbun.data.source.network.ApiFactory
import ru.topbun.data.source.network.auth.AuthApi
import ru.topbun.data.source.network.category.CategoryApi
import ru.topbun.data.source.network.recipe.RecipeApi

val ktorModule = module {
    single<ApiFactory> { ApiFactory() }
    single<RecipeApi> { RecipeApi(get()) }
    single<CategoryApi> { CategoryApi(get())}
    single<AuthApi> { AuthApi(get())}
}