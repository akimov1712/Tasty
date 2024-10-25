package ru.topbun.tasty.domain.useCases.auth

import ru.topbun.tasty.domain.entity.auth.LoginEntity
import ru.topbun.tasty.domain.entity.auth.SignUpEntity
import ru.topbun.tasty.domain.repository.auth.LoginRepository
import ru.topbun.tasty.domain.repository.auth.SignUpRepository

class SignUpUseCase(
    private val repository: SignUpRepository
) {

    suspend operator fun invoke(signUp: SignUpEntity) = repository.signUp(signUp)

}