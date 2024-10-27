package ru.topbun.tasty.data.source.remote.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @Serializable val token: String
)
