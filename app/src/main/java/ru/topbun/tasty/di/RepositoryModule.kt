package ru.topbun.tasty.di

import org.koin.dsl.module
import ru.topbun.tasty.data.repository.AuthRepositoryImpl
import ru.topbun.tasty.data.repository.CategoryRepositoryImpl
import ru.topbun.tasty.data.repository.RecipeRepositoryImpl
import ru.topbun.tasty.domain.repository.auth.AuthRepository
import ru.topbun.tasty.domain.repository.category.CategoryRepository
import ru.topbun.tasty.domain.repository.recipe.RecipeRepository
import java.util.Locale.Category

val repositoryModule = module {
    single<AuthRepository>{ AuthRepositoryImpl(get(), get()) }
    single<CategoryRepository>{ CategoryRepositoryImpl(get()) }
    single<RecipeRepository>{ RecipeRepositoryImpl(get()) }
}