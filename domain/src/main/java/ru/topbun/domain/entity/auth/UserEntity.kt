package ru.topbun.domain.entity.auth

data class UserEntity(
    val id: Int,
    val username: String,
    val email: String,
    val isAdmin: Boolean
)
