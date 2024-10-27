package ru.topbun.data.di

import org.koin.dsl.module
import ru.topbun.data.repository.AuthRepositoryImpl
import ru.topbun.tasty.data.repository.CategoryRepositoryImpl
import ru.topbun.tasty.data.repository.RecipeRepositoryImpl

val repositoryModule = module {
    single<ru.topbun.domain.repository.auth.AuthRepository>{ AuthRepositoryImpl(get(), get()) }
    single<ru.topbun.domain.repository.category.CategoryRepository>{ CategoryRepositoryImpl(get()) }
    single<ru.topbun.domain.repository.recipe.RecipeRepository>{ RecipeRepositoryImpl(get()) }
}