package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.repository.recipe.RecipeRepository

class GetRecipeWithIdUseCase(
    private val repository: ru.topbun.domain.repository.recipe.RecipeRepository
) {

    suspend operator fun invoke(id: Int) = repository.getRecipesWithId(id)

}