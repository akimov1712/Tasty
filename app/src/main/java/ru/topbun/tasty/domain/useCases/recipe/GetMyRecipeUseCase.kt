package ru.topbun.tasty.domain.useCases.recipe

import ru.topbun.tasty.domain.repository.recipe.RecipeRepository

class GetMyRecipeUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(offset: Int = 0, limit: Int = 20) = repository.getMyRecipes(offset, limit)

}
