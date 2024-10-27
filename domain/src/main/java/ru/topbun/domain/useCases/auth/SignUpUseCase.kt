package ru.topbun.domain.useCases.auth

import ru.topbun.domain.entity.auth.SignUpEntity
import ru.topbun.domain.repository.auth.AuthRepository

class SignUpUseCase(
    private val repository: ru.topbun.domain.repository.auth.AuthRepository
) {

    suspend operator fun invoke(signUp: ru.topbun.domain.entity.auth.SignUpEntity) = repository.signUp(signUp)

}