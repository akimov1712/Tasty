package ru.topbun.profile

import ru.topbun.domain.entity.auth.UserEntity

data class ProfileState(
    val screenState: ProfileScreenState = ProfileScreenState.Loading
){

    sealed interface ProfileScreenState{
        data object Loading: ProfileScreenState
        data class Success(val user: UserEntity): ProfileScreenState
        data class Error(val msg: String): ProfileScreenState
        data object NotAuth: ProfileScreenState
    }

}
