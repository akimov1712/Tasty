package ru.topbun.auth.fragments.login

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.auth.fragments.login.LoginState.LoginScreenState.*
import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.useCases.auth.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
): ScreenModelState<LoginState>(LoginState()) {

    fun login() = screenModelScope.launch {
        wrapperException({
            updateState { copy(loginScreenState = Loading) }
            val login = LoginEntity(
                email = stateValue.email,
                password = stateValue.password,
            )
            loginUseCase(login)
            updateState { copy(loginScreenState = Success) }
        }){
            updateState { copy(loginScreenState = Error(it)) }
        }
    }

    fun changeEmail(email: String) = updateState { copy(email = email) }
    fun changePassword(password: String) = updateState { copy(password = password) }
    fun changePasswordVisible() = updateState { copy(showPassword = !showPassword) }

}