package ru.topbun.tasty.data.mapper

import ru.topbun.tasty.data.source.remote.category.dto.CategoryDTO
import ru.topbun.tasty.domain.entity.category.CategoryEntity

fun CategoryDTO.toEntity() = CategoryEntity(
    id = id,
    name = name,
    image = image
)

fun List<CategoryDTO>.toEntity() = map{it.toEntity()}

