package ru.topbun.domain.useCases.auth

import ru.topbun.domain.repository.auth.AuthRepository

class CheckExistsTokenUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() = repository.checkExistsToken()

}