package ru.topbun.domain.entity.auth

data class SignUpEntity(
    val email: String,
    val username: String,
    val password: String,
)
