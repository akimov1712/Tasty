package ru.topbun.tasty.domain.repository.auth

import ru.topbun.tasty.domain.entity.auth.LoginEntity
import ru.topbun.tasty.domain.entity.auth.SignUpEntity

interface AuthRepository {

    suspend fun login(login: LoginEntity)
    suspend fun signUp(signup: SignUpEntity)

}