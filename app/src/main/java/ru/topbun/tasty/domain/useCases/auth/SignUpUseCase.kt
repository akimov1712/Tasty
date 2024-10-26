package ru.topbun.tasty.domain.useCases.auth

import ru.topbun.tasty.domain.entity.auth.SignUpEntity
import ru.topbun.tasty.domain.repository.auth.AuthRepository

class SignUpUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(signUp: SignUpEntity) = repository.signUp(signUp)

}