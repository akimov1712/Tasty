package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.repository.recipe.RecipeRepository

class DeleteRecipeUseCase(
    private val repository: ru.topbun.domain.repository.recipe.RecipeRepository
) {

    suspend operator fun invoke(id: Int, fromCache: Boolean = false) = repository.deleteRecipe(id, fromCache)

}
