package ru.topbun.tasty.data.repository

import ru.topbun.tasty.data.mapper.toDTO
import ru.topbun.tasty.data.source.local.dataStore.Settings
import ru.topbun.tasty.data.source.local.dataStore.saveToken
import ru.topbun.tasty.data.source.remote.auth.AuthApi
import ru.topbun.tasty.domain.entity.auth.LoginEntity
import ru.topbun.tasty.domain.entity.auth.SignUpEntity
import ru.topbun.tasty.domain.repository.auth.AuthRepository
import ru.topbun.tasty.utills.codeResultWrapper
import ru.topbun.tasty.utills.exceptionWrapper
import kotlin.math.sign

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val settings: Settings,
): AuthRepository {

    override suspend fun login(login: LoginEntity): Unit = exceptionWrapper{
        val token = api.login(login.toDTO()).codeResultWrapper()
        settings.saveToken(token.token)
    }

    override suspend fun signUp(signup: SignUpEntity): Unit = exceptionWrapper{
        val token = api.signUp(signup.toDTO()).codeResultWrapper()
        settings.saveToken(token.token)
    }
}