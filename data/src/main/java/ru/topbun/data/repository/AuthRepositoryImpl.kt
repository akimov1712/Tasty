package ru.topbun.data.repository

import ru.topbun.android.codeResultWrapper
import ru.topbun.android.exceptionWrapper
import ru.topbun.tasty.mapper.toDTO
import ru.topbun.data.source.local.dataStore.Settings
import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.entity.auth.SignUpEntity
import ru.topbun.domain.repository.auth.AuthRepository
import ru.topbun.data.source.local.dataStore.saveToken
import ru.topbun.tasty.data.source.remote.auth.AuthApi

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val settings: Settings,
): AuthRepository {

    override suspend fun login(login: LoginEntity): Unit = exceptionWrapper {
        val token = api.login(login.toDTO()).codeResultWrapper()
        settings.saveToken(token.token)
    }

    override suspend fun signUp(signup: SignUpEntity): Unit = exceptionWrapper {
        val token = api.signUp(signup.toDTO()).codeResultWrapper()
        settings.saveToken(token.token)
    }
}