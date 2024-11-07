package ru.topbun.auth.fragments.login

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
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
                email = stateValue.email.trim(),
                password = stateValue.password.trim(),
            )
            loginUseCase(login)
            updateState { copy(loginScreenState = Success) }
        }){ _, msg ->
            updateState { copy(loginScreenState = Error(msg)) }
        }
    }

    fun changeEmail(email: String) = updateState { copy(email = email, validFields = validFields(email, password)) }
    fun changePassword(password: String) = updateState { copy(password = password, validFields = validFields(email, password)) }
    fun changePasswordVisible() = updateState { copy(showPassword = !showPassword) }

    private fun validFields(email: String, password: String) = password.isNotEmpty() && email.isNotEmpty()

}