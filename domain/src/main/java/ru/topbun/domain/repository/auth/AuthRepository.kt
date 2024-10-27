package ru.topbun.domain.repository.auth

import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.entity.auth.SignUpEntity

interface AuthRepository {

    suspend fun login(login: ru.topbun.domain.entity.auth.LoginEntity)
    suspend fun signUp(signup: ru.topbun.domain.entity.auth.SignUpEntity)

}