package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.repository.recipe.RecipeRepository

class GetRecipeUseCase(
    private val repository: ru.topbun.domain.repository.recipe.RecipeRepository
) {

    suspend operator fun invoke(offset: Int = 0, limit: Int = 20) = repository.getRecipes(offset, limit)

}
