package ru.topbun.data.mapper

import ru.topbun.domain.entity.recipe.IngredientsEntity
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.entity.recipe.StepEntity
import ru.topbun.tasty.data.source.remote.recipe.dto.IngredientsDTO
import ru.topbun.tasty.data.source.remote.recipe.dto.RecipeDTO
import ru.topbun.tasty.data.source.remote.recipe.dto.StepDTO

fun RecipeEntity.toDTO() = RecipeDTO(
    id = id,
    userId = userId,
    title = title,
    description = description,
    image = image,
    categoryId = categoryId,
    timeCooking = timeCooking,
    difficulty = difficulty,
    carbs = carbs,
    fat = fat,
    protein = protein,
    kcal = kcal,
    ingredients = ingredients.map { it.toDTO() },
    steps = steps.map { it.toDTO() }
)

fun StepEntity.toDTO() = StepDTO(
    id = id,
    text = text,
    preview = preview,
    order = order,
)

fun IngredientsEntity.toDTO() = IngredientsDTO(
    id = id,
    name = name,
    value = value,
)

fun RecipeDTO.toEntity() = RecipeEntity(
    id = id,
    userId = userId,
    title = title,
    description = description,
    image = image,
    categoryId = categoryId,
    timeCooking = timeCooking,
    difficulty = difficulty,
    carbs = carbs,
    fat = fat,
    protein = protein,
    kcal = kcal,
    ingredients = ingredients.map { it.toEntity() },
    steps = steps.map { it.toEntity() }
)

fun List<RecipeDTO>.toEntity() = map { it.toEntity() }

fun StepDTO.toEntity() = StepEntity(
    id = id,
    text = text,
    preview = preview,
    order = order,
)

fun IngredientsDTO.toEntity() = IngredientsEntity(
    id = id,
    name = name,
    value = value,
)

