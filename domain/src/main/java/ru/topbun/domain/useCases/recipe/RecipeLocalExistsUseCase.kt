package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.entity.recipe.RecipeTabs
import ru.topbun.domain.repository.recipe.RecipeRepository

class RecipeLocalExistsUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(id: Int) = repository.recipeLocalExists(id)

}
