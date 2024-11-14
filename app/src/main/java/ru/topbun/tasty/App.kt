package ru.topbun.tasty

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.topbun.auth.AuthScreen
import ru.topbun.detail_recipe.DetailRecipeScreen
import ru.topbun.navigation.main.MainScreenProvider
import ru.topbun.recipe_by_category.RecipeByCategoryScreen
import ru.topbun.tabs.TabsScreen
import ru.topbun.tasty.di.appModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        connectMetrics()
        registerKoin()
        registerScreens()
    }

    private fun connectMetrics(){
        val config = AppMetricaConfig.newConfigBuilder(BuildConfig.API_KEY_METRIC).build()
        AppMetrica.activate(this, config)
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
                DetailRecipeScreen(provider.recipeId, provider.fromCache)
            }
            register<MainScreenProvider.RecipeByCategory> { provider ->
                RecipeByCategoryScreen(provider.category)
            }
        }
    }

}