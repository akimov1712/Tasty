package ru.topbun.tasty.di

import org.koin.dsl.module
import ru.topbun.data.di.dataStoreModule
import ru.topbun.data.di.repositoryModule
import ru.topbun.data.di.ktorModule

val appModule = module {
    includes(ktorModule, dataStoreModule, repositoryModule, useCasesModule, featuresModule)
}