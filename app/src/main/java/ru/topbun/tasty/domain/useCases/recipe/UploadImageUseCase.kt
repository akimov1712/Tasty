package ru.topbun.tasty.domain.useCases.recipe

import ru.topbun.tasty.domain.repository.recipe.RecipeRepository

class UploadImageUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(image: ByteArray) = repository.uploadImage(image)

}