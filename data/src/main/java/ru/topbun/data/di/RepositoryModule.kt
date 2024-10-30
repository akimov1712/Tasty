package ru.topbun.data.di

import org.koin.core.scope.get
import org.koin.dsl.module
import ru.topbun.data.repository.AuthRepositoryImpl
import ru.topbun.data.repository.CategoryRepositoryImpl
import ru.topbun.data.repository.RecipeRepositoryImpl
import ru.topbun.domain.repository.auth.AuthRepository
import ru.topbun.domain.repository.category.CategoryRepository
import ru.topbun.domain.repository.recipe.RecipeRepository

val repositoryModule = module {
    single<AuthRepository>{ AuthRepositoryImpl(get(), get()) }
    single<CategoryRepository>{ CategoryRepositoryImpl(get()) }
    single<RecipeRepository>{ RecipeRepositoryImpl(get(), get()) }
}