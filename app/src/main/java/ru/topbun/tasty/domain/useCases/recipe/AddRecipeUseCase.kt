package ru.topbun.tasty.domain.useCases.recipe

import ru.topbun.tasty.domain.entity.recipe.RecipeEntity
import ru.topbun.tasty.domain.repository.recipe.RecipeRepository

class AddRecipeUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(recipe: RecipeEntity) = repository.addRecipe(recipe)

}
