package ru.topbun.data.repository

import io.ktor.client.call.body
import ru.topbun.android.FailedExtractTokenException
import ru.topbun.data.codeResultWrapper
import ru.topbun.data.exceptionWrapper
import ru.topbun.data.source.local.dataStore.Settings
import ru.topbun.data.source.local.dataStore.getToken
import ru.topbun.data.source.local.dataStore.saveToken
import ru.topbun.data.source.network.auth.AuthApi
import ru.topbun.data.source.network.auth.dto.UserDTO
import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.entity.auth.SignUpEntity
import ru.topbun.domain.repository.auth.AuthRepository
import ru.topbun.tasty.data.source.remote.auth.dto.TokenResponse
import ru.topbun.tasty.mapper.toDTO
import ru.topbun.tasty.mapper.toEntity

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val settings: Settings,
): AuthRepository {

    override suspend fun login(login: LoginEntity): Unit = exceptionWrapper {
        val token = api.login(login.toDTO()).codeResultWrapper().body<TokenResponse>()
        settings.saveToken(token.token)
    }

    override suspend fun signUp(signup: SignUpEntity): Unit = exceptionWrapper {
        val token = api.signUp(signup.toDTO()).codeResultWrapper().body<TokenResponse>()
        settings.saveToken(token.token)
    }

    override suspend fun getAccountInfo() = exceptionWrapper{
        api.getAccountInfo(settings.getToken()).codeResultWrapper().body<UserDTO>().toEntity()
    }

    override suspend fun checkExistsToken() = exceptionWrapper{
        if (settings.getToken().isEmpty()) throw FailedExtractTokenException()
    }

    override suspend fun logout() = exceptionWrapper {
        settings.saveToken("")
    }
}