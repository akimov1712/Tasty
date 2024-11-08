package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.repository.recipe.RecipeRepository

class GetFavoritesRecipeUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke() = repository.getFavoritesRecipes()

}
