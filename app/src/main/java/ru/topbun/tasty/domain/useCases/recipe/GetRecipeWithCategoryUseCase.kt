package ru.topbun.tasty.domain.useCases.recipe

import ru.topbun.tasty.domain.repository.recipe.RecipeRepository

class GetRecipeWithCategoryUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(categoryId: Int, offset: Int = 0, limit: Int = 20) = repository.getRecipesWithCategory(categoryId, offset, limit)

}
