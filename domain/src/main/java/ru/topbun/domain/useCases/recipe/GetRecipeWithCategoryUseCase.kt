package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.repository.recipe.RecipeRepository

class GetRecipeWithCategoryUseCase(
    private val repository: ru.topbun.domain.repository.recipe.RecipeRepository
) {

    suspend operator fun invoke(categoryId: Int, offset: Int = 0, limit: Int = 20) = repository.getRecipesWithCategory(categoryId, offset, limit)

}
