package ru.topbun.tasty.di

import org.koin.dsl.module
import ru.topbun.domain.useCases.auth.LoginUseCase
import ru.topbun.domain.useCases.auth.SignUpUseCase
import ru.topbun.domain.useCases.category.GetCategoriesUseCase
import ru.topbun.domain.useCases.category.GetCategoriesWithIdUseCase
import ru.topbun.domain.useCases.recipe.AddRecipeUseCase
import ru.topbun.domain.useCases.recipe.GetMyRecipeUseCase
import ru.topbun.domain.useCases.recipe.GetRecipeWithCategoryUseCase
import ru.topbun.domain.useCases.recipe.GetRecipeWithIdUseCase
import ru.topbun.domain.useCases.recipe.UploadImageUseCase

val useCasesModule = module {
    single<LoginUseCase> {
        LoginUseCase(
            get()
        )
    }
    single<SignUpUseCase> {
        SignUpUseCase(
            get()
        )
    }
    single<GetCategoriesUseCase> {
        GetCategoriesUseCase(
            get()
        )
    }
    single<GetCategoriesWithIdUseCase> {
        GetCategoriesWithIdUseCase(
            get()
        )
    }
    single<AddRecipeUseCase> {
        AddRecipeUseCase(
            get()
        )
    }
    single<GetMyRecipeUseCase> {
        GetMyRecipeUseCase(
            get()
        )
    }
    single<GetRecipeWithCategoryUseCase> {
        GetRecipeWithCategoryUseCase(
            get()
        )
    }
    single<GetRecipeWithIdUseCase> {
        GetRecipeWithIdUseCase(
            get()
        )
    }
    single<UploadImageUseCase> {
        UploadImageUseCase(
            get()
        )
    }
}