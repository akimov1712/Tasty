package ru.topbun.tasty

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.topbun.tabs.tabsScreenModule
import ru.topbun.tasty.di.appModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        registerKoin()
        registerScreens()
    }

    private fun registerKoin(){
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }

    private fun registerScreens(){
        ScreenRegistry {
            tabsScreenModule()
        }
    }

}