package ru.topbun.data.source.network.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerialName("id") val id: Int,
    @SerialName("username") val username: String,
    @SerialName("email") val email: String,
    @SerialName("isAdmin") val isAdmin: Boolean
)