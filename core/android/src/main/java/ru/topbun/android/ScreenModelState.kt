package ru.topbun.android

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update

open class ScreenModelState<T>(initialState: T): StateScreenModel<T>(initialState) {

    val stateValue get() = state.value

    open fun updateState(update: T.() -> T) {
        mutableState.value = state.value.update()
    }

}