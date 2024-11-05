package ru.topbun.domain.repository.auth

import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.entity.auth.SignUpEntity

interface AuthRepository {

    suspend fun login(login: LoginEntity)
    suspend fun signUp(signup: SignUpEntity)

}