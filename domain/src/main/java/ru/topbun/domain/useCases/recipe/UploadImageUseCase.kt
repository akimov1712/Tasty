package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.repository.recipe.RecipeRepository

class UploadImageUseCase(
    private val repository: ru.topbun.domain.repository.recipe.RecipeRepository
) {

    suspend operator fun invoke(image: ByteArray) = repository.uploadImage(image)

}