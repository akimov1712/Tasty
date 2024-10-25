package ru.topbun.tasty.domain.useCases.recipe

import ru.topbun.tasty.domain.repository.recipe.RecipeRepository

class GetRecipeWithCategoryUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(categoryId: Int) = repository.getRecipesWithCategory(categoryId)

}
