package ru.topbun.domain.useCases.auth

import ru.topbun.domain.repository.auth.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() = repository.logout()

}