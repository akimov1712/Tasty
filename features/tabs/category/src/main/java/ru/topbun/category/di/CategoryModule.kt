package ru.topbun.category.di

import org.koin.dsl.module
import ru.topbun.category.CategoryViewModel

val categoryModule = module {
    single { CategoryViewModel(get()) }
}