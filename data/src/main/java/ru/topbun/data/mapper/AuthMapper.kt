package ru.topbun.tasty.mapper

import ru.topbun.data.source.network.auth.dto.UserDTO
import ru.topbun.domain.entity.auth.LoginEntity
import ru.topbun.domain.entity.auth.SignUpEntity
import ru.topbun.domain.entity.auth.UserEntity
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

fun UserDTO.toEntity() = UserEntity(
    id = id,
    email = email,
    username = username,
    isAdmin = isAdmin
)