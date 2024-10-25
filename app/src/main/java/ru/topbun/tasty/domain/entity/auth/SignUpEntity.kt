package ru.topbun.tasty.domain.entity.auth

data class SignUpEntity(
    val email: String,
    val username: String,
    val password: String,
)
