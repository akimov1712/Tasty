package ru.topbun.domain.useCases.auth

import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.repository.auth.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(login: LoginEntity) = repository.login(login)

}