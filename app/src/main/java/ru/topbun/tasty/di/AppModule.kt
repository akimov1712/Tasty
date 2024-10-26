package ru.topbun.tasty.di

import org.koin.dsl.module

val appModule = module {
    includes(retrofitModule, dataStoreModule, repositoryModule, useCasesModule)
}