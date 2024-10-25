package ru.topbun.tasty.domain.repository.auth

import ru.topbun.tasty.domain.entity.auth.LoginEntity

interface LoginRepository {

    suspend fun login(login: LoginEntity)

}