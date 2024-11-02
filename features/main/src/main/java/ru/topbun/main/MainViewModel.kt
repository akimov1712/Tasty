package ru.topbun.main

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.topbun.android.ScreenModelState
import ru.topbun.tabs.TabsScreen

class MainViewModel: ScreenModelState<MainState>(MainState()) {

    fun setScreen(screen: Screen) = updateState { copy(screen = screen) }

}
