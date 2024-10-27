package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.repository.recipe.RecipeRepository

class GetMyRecipeUseCase(
    private val repository: ru.topbun.domain.repository.recipe.RecipeRepository
) {

    suspend operator fun invoke(offset: Int = 0, limit: Int = 20) = repository.getMyRecipes(offset, limit)

}
