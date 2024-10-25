package ru.topbun.tasty.domain.useCases.recipe

import ru.topbun.tasty.domain.repository.recipe.RecipeRepository

class GetRecipeWithIdUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(id: Int) = repository.getRecipesWithId(id)

}