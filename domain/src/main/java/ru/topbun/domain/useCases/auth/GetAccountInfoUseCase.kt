package ru.topbun.domain.useCases.auth

import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.repository.auth.AuthRepository

class GetAccountInfoUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() = repository.getAccountInfo()

}