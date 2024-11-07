package ru.topbun.android

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.registry.screenModule
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

data class ScreenModelNavigatorState(
    val screen: Screen? = null,
    val updateCount: Int = 0,
)

open class ScreenModelNavigator : ScreenModelState<ScreenModelNavigatorState>(ScreenModelNavigatorState()) {

    fun pushScreen(screen: Screen) {
        updateState { copy(screen = screen, updateCount = state.value.updateCount + 1) }
    }

    fun popScreen() {
        updateState { copy(updateCount = state.value.updateCount + 1, screen = null) }
    }
}