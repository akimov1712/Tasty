package ru.topbun.tasty.data.source.remote.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpDTO(
    @SerialName("email") val email: String,
    @SerialName("username") val username: String,
    @SerialName("password") val password: String,
)
