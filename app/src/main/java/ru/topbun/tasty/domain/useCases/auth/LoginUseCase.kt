package ru.topbun.tasty.domain.useCases.auth

import ru.topbun.tasty.domain.entity.auth.LoginEntity
import ru.topbun.tasty.domain.repository.auth.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(login: LoginEntity) = repository.login(login)

}