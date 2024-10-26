package ru.topbun.tasty.di

import android.content.Context
import org.koin.core.scope.get
import org.koin.dsl.module
import ru.topbun.tasty.data.source.local.dataStore.AppSettings.dataStore
import ru.topbun.tasty.data.source.local.dataStore.Settings

val dataStoreModule = module {
    single<Settings>{
        val context = get<Context>()
        context.dataStore
    }
}