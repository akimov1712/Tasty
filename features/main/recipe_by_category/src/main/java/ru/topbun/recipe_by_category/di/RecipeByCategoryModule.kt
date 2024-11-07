package ru.topbun.recipe_by_category.di

import org.koin.dsl.module
import ru.topbun.recipe_by_category.RecipeByCategoryViewModel

val recipeByCategoryModule = module {
    factory { (categoryId: Int) ->
        RecipeByCategoryViewModel(categoryId, get())
    }
}