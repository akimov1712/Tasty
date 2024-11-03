package ru.topbun.tasty

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.topbun.auth.AuthScreen
import ru.topbun.detail_recipe.DetailRecipeScreen
import ru.topbun.navigation.main.MainScreenProvider
import ru.topbun.tabs.TabsScreen
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
            register<MainScreenProvider.Tabs> {
                TabsScreen
            }
            register<MainScreenProvider.Auth> {
                AuthScreen
            }
            register<MainScreenProvider.DetailRecipe> { provider ->
                DetailRecipeScreen(provider.recipe)
            }
        }
    }

}