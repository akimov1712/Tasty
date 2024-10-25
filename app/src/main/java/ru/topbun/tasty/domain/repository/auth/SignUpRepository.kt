package ru.topbun.tasty.domain.repository.auth

import ru.topbun.tasty.domain.entity.auth.SignUpEntity

interface SignUpRepository {

    suspend fun signUp(signup: SignUpEntity)

}