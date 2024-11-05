package ru.topbun.auth.fragments.signUp

import android.util.Log

data class SignUpState(
    val username: String = "",
    val usernameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val validFields: Boolean = false,
    val signUpScreenState: SignUpScreenState = SignUpScreenState.Initial
){
    
    sealed interface SignUpScreenState{
        
        data object Initial: SignUpScreenState
        data object Loading: SignUpScreenState
        data object Success: SignUpScreenState
        data class Error(val msg: String): SignUpScreenState
        
    }
    
}
