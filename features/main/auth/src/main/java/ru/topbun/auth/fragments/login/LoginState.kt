package ru.topbun.auth.fragments.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val validFields: Boolean = password.isNotEmpty() && email.isNotEmpty(),
    val loginScreenState: LoginScreenState = LoginScreenState.Initial
){
    
    sealed interface LoginScreenState{
        
        data object Initial: LoginScreenState
        data object Loading: LoginScreenState
        data object Success: LoginScreenState
        data class Error(val msg: String): LoginScreenState
        
    }
    
}
