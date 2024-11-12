package ru.topbun.data.mapper

import ru.topbun.data.source.local.database.dbo.CategoryDBO
import ru.topbun.data.source.local.database.dbo.IngredientsDBO
import ru.topbun.data.source.local.database.dbo.RecipeDBO
import ru.topbun.data.source.local.database.dbo.StepDBO
import ru.topbun.domain.entity.recipe.IngredientsEntity
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.entity.recipe.StepEntity
import ru.topbun.tasty.data.source.remote.recipe.dto.IngredientsDTO
import ru.topbun.data.source.network.recipe.dto.RecipeDTO
import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.tasty.data.source.remote.recipe.dto.StepDTO

fun RecipeEntity.toDTO() = RecipeDTO(
    id = id,
    userId = userId,
    title = title,
    description = description,
    image = image,
    isFavorite = isFavorite,
    categories = categories.toDTO(),
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
    isFavorite = isFavorite,
    image = image,
    categories = categories.toEntity(),
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

fun CategoryDBO.toEntity() = CategoryEntity(
    id = id,
    name = name,
    image = image
)

fun IngredientsDBO.toEntity() = IngredientsEntity(
    id = id,
    name = name,
    value = value
)

fun StepDBO.toEntity() = StepEntity(
    id = id,
    text = text,
    preview = preview,
    order = order
)

fun RecipeDBO.toEntity() = RecipeEntity(
    id = id,
    userId = userId,
    title = title,
    description = description,
    isFavorite = isFavorite,
    image = image,
    categories = categories.map { it.toEntity() },
    timeCooking = timeCooking,
    difficulty = difficulty,
    carbs = carbs,
    fat = fat,
    protein = protein,
    kcal = kcal,
    ingredients = ingredients.map { it.toEntity() },
    steps = steps.map { it.toEntity() }
)

fun CategoryEntity.toDBO() = CategoryDBO(
    id = id,
    name = name,
    image = image
)

fun IngredientsEntity.toDBO() = IngredientsDBO(
    id = id,
    name = name,
    value = value
)

fun StepEntity.toDBO() = StepDBO(
    id = id,
    text = text,
    preview = preview,
    order = order
)

fun RecipeEntity.toDBO() = RecipeDBO(
    id = id,
    userId = userId,
    title = title,
    description = description,
    isFavorite = isFavorite,
    image = image,
    categories = categories.map { it.toDBO() },
    timeCooking = timeCooking,
    difficulty = difficulty,
    carbs = carbs,
    fat = fat,
    protein = protein,
    kcal = kcal,
    ingredients = ingredients.map { it.toDBO() },
    steps = steps.map { it.toDBO() }
)

