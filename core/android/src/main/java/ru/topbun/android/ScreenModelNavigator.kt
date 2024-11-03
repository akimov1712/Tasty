package ru.topbun.android

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.registry.screenModule
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module


data class ScreenModelNavigatorState(
    val screen: Screen? = null
)

open class ScreenModelNavigator: ScreenModelState<ScreenModelNavigatorState>(ScreenModelNavigatorState()) {

    fun pushScreen(screen: Screen) = updateState { copy(screen = screen) }

}