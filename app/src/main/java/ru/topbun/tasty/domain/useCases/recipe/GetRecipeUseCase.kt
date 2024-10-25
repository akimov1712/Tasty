package ru.topbun.tasty.domain.useCases.recipe

import ru.topbun.tasty.domain.repository.recipe.RecipeRepository

class GetRecipeUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(offset: Int = 0, limit: Int = 20) = repository.getRecipes(offset, limit)

}
