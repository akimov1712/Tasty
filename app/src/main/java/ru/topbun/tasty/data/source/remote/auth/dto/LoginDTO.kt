package ru.topbun.tasty.data.source.remote.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
)
