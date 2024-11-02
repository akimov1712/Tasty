package ru.topbun.data.mapper

import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.tasty.data.source.remote.category.dto.CategoryDTO

fun CategoryDTO.toEntity() = CategoryEntity(
    id = id,
    name = name,
    image = image
)

fun CategoryEntity.toDTO() = CategoryDTO(
    id = id,
    name = name,
    image = image
)

fun List<CategoryDTO>.toEntity() = map{it.toEntity()}
fun List<CategoryEntity>.toDTO() = map{it.toDTO()}

