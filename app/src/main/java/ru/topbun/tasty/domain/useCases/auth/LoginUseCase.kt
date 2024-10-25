package ru.topbun.tasty.domain.useCases.auth

import ru.topbun.tasty.domain.entity.auth.LoginEntity
import ru.topbun.tasty.domain.repository.auth.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(login: LoginEntity) = repository.login(login)

}