package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.repository.recipe.RecipeRepository

class ChangeFavoriteUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(recipeId: Int, isFavorite: Boolean) = repository.changeFavorite(recipeId, isFavorite)

}
