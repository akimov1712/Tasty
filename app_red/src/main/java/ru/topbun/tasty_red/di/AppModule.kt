package ru.topbun.tasty_red.di

import org.koin.dsl.module
import ru.topbun.data.di.dataStoreModule
import ru.topbun.data.di.databaseModule
import ru.topbun.data.di.ktorModule
import ru.topbun.data.di.repositoryModule
import ru.topbun.navigation.navigationModule

val appModule = module {
    includes(databaseModule, ktorModule, dataStoreModule, repositoryModule, useCasesModule, featuresModule, navigationModule)
}