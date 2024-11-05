package ru.topbun.auth.fragments.signUp

import android.util.Log
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.auth.fragments.signUp.SignUpState.SignUpScreenState
import ru.topbun.auth.fragments.signUp.SignUpState.SignUpScreenState.*
import ru.topbun.common.validationEmail
import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.entity.auth.SignUpEntity
import ru.topbun.domain.useCases.auth.SignUpUseCase

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
): ScreenModelState<SignUpState>(SignUpState()) {

    fun signUp() = screenModelScope.launch {
        wrapperException({
            updateState { copy(signUpScreenState = Loading) }
            val signUp = SignUpEntity(
                username = stateValue.username.trim(),
                email = stateValue.email.trim(),
                password = stateValue.password.trim(),
            )
            signUpUseCase(signUp)
            updateState { copy(signUpScreenState = Success) }
        }){
            updateState { copy(signUpScreenState = Error(it)) }
        }
    }

    fun changeUsername(username: String) {
        updateState {
            copy(
                username = username,
                usernameError = !username.validUsername()
            )
        }
        checkValidFields()
}

    fun changeEmail(email: String) {
        updateState {
            copy(
                email = email,
                emailError = !email.validEmail()
            )
        }
        checkValidFields()
    }

    fun changePassword(password: String) {
        updateState {
            copy(
                password = password,
                passwordError = !password.validPassword()
            )
        }
        checkValidFields()
    }

    fun changeConfirmPassword(confirmPassword: String) {
        updateState {
            copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = !confirmPassword.validConfirmPassword()
            )
        }
        checkValidFields()
    }

    fun changePasswordVisible() = updateState {
        copy(showPassword = !showPassword)
    }

    fun changeConfirmPasswordVisible() = updateState {
        copy(showConfirmPassword = !showConfirmPassword)
    }


    private fun String.validUsername() = this.length in 4..48
    private fun String.validEmail() = this.validationEmail()
    private fun String.validPassword() = this.length in 6..64
    private fun String.validConfirmPassword() = this == stateValue.password

    private fun checkValidFields(){
        val validFields = listOf(
            stateValue.username.validUsername(),
            stateValue.email.validEmail(),
            stateValue.password.validPassword(),
            stateValue.confirmPassword.validConfirmPassword()
        ).contains(false)
        updateState { copy(validFields = !validFields) }
    }

}