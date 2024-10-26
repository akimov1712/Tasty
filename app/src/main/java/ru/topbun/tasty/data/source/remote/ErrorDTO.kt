package ru.topbun.tasty.data.source.remote

import kotlinx.serialization.Serializable


@Serializable
data class ErrorDTO(
    val code: Int,
    val message: String,
)
