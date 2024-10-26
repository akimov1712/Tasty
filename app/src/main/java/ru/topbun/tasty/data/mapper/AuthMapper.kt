package ru.topbun.tasty.data.mapper

import ru.topbun.tasty.data.source.remote.auth.dto.LoginDTO
import ru.topbun.tasty.data.source.remote.auth.dto.SignUpDTO
import ru.topbun.tasty.domain.entity.auth.LoginEntity
import ru.topbun.tasty.domain.entity.auth.SignUpEntity

fun LoginEntity.toDTO() = LoginDTO(
    email = email,
    password = password,
)

fun SignUpEntity.toDTO() = SignUpDTO(
    email = email,
    username = username,
    password = password,
)