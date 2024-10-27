package ru.topbun.tasty.mapper

import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.entity.auth.SignUpEntity
import ru.topbun.tasty.data.source.remote.auth.dto.LoginDTO
import ru.topbun.tasty.data.source.remote.auth.dto.SignUpDTO

fun LoginEntity.toDTO() = LoginDTO(
    email = email,
    password = password,
)

fun SignUpEntity.toDTO() = SignUpDTO(
    email = email,
    username = username,
    password = password,
)