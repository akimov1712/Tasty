package ru.topbun.tasty.di

import org.koin.dsl.module
import ru.topbun.data.di.dataStoreModule
import ru.topbun.data.di.repositoryModule
import ru.topbun.data.di.retrofitModule

val appModule = module {
    includes(retrofitModule, dataStoreModule, repositoryModule, useCasesModule)
}