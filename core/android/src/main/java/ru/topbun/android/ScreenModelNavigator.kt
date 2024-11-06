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
    val updateCount: Int = 0
)

open class ScreenModelNavigator : ScreenModel {

    private val _state = MutableStateFlow(ScreenModelNavigatorState())
    val state: StateFlow<ScreenModelNavigatorState>
        get() = _state

    fun pushScreen(screen: Screen) {
        _state.value = state.value.copy(screen = screen, updateCount = state.value.updateCount + 1)
    }
}