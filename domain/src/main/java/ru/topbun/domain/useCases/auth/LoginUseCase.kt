package ru.topbun.domain.useCases.auth

import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.repository.auth.AuthRepository

class LoginUseCase(
    private val repository: ru.topbun.domain.repository.auth.AuthRepository
) {

    suspend operator fun invoke(login: ru.topbun.domain.entity.auth.LoginEntity) = repository.login(login)

}