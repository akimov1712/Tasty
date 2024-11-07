package ru.topbun.domain.repository.auth

import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.entity.auth.SignUpEntity
import ru.topbun.domain.entity.auth.UserEntity

interface AuthRepository {

    suspend fun login(login: LoginEntity)
    suspend fun signUp(signup: SignUpEntity)
    suspend fun getAccountInfo(): UserEntity
    suspend fun checkExistsToken()
    suspend fun logout()

}