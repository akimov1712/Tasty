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

    fun changeUsername(username: String) = updateState {
        copy(username = username, validFields = validFields(username, email, password, confirmPassword))
    }

    fun changeEmail(email: String) = updateState {
        copy(email = email, validFields = validFields(username, email, password, confirmPassword))
    }

    fun changePassword(password: String) = updateState {
        copy(password = password, validFields = validFields(username, email, password, confirmPassword))
    }

    fun changeConfirmPassword(confirmPassword: String) = updateState {
        copy(confirmPassword = confirmPassword, validFields = validFields(username, email, password, confirmPassword))
    }

    fun changePasswordVisible() = updateState {
        copy(showPassword = !showPassword)
    }

    fun changeConfirmPasswordVisible() = updateState {
        copy(showConfirmPassword = !showConfirmPassword)
    }

    private fun validFields(username: String, email: String, password: String, passwordConfirm: String): Boolean{
        val validValues = listOf(
            username.validUsername(),
            email.validEmail(),
            password.validPassword(),
            passwordConfirm.validConfirmPassword()
        )
        return !validValues.contains(false)
    }

    private fun String.validUsername() = if (this.length < 4){
        updateState { copy(usernameError = "Псевдоним не может быть меньше 4 символов") }
        Log.d("USERNAME_ERROR", stateValue.usernameError.toString())
        Log.d("USERNAME_ERROR", stateValue.emailError.toString())
        Log.d("USERNAME_ERROR", stateValue.passwordError.toString())
        Log.d("USERNAME_ERROR", stateValue.confirmPasswordError.toString())
        false
    } else {
        updateState { copy(usernameError = null) }; true
    }

    private fun String.validEmail() = if (this.validationEmail()){
        updateState { copy(emailError = "Неверный формат эл. почты") }
        false
    } else {
        updateState { copy(emailError = null) }; true
    }

    private fun String.validPassword() = if (this.length < 6){
        updateState { copy(passwordError = "Пароль не может быть меньше 6 символов") }
        false
    } else {
        updateState { copy(passwordError = null) }; true
    }

    private fun String.validConfirmPassword() = if (this != stateValue.password){
        updateState { copy(confirmPasswordError = "Пароль не совпадают") }
        false
    } else {
        updateState { copy(confirmPasswordError = null) }; true
    }

}