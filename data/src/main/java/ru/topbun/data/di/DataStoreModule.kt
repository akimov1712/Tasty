package ru.topbun.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import org.koin.core.scope.get
import org.koin.dsl.module
import ru.topbun.data.source.local.dataStore.AppSettings.dataStore
import ru.topbun.data.source.local.dataStore.Settings
import java.util.prefs.Preferences

val dataStoreModule = module {
    single<Settings>{
        val context = get<Context>()
        context.dataStore
    }
}