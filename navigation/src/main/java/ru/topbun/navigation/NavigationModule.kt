package ru.topbun.navigation

import org.koin.dsl.module
import ru.topbun.android.ScreenModelNavigator
import ru.topbun.navigation.main.MainScreenNavigator

val navigationModule = module {
    single<MainScreenNavigator>{ MainScreenNavigator() }
}