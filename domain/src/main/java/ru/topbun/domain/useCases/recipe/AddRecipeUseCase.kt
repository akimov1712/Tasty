package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.repository.recipe.RecipeRepository

class AddRecipeUseCase(
    private val repository: ru.topbun.domain.repository.recipe.RecipeRepository
) {

    suspend operator fun invoke(recipe: ru.topbun.domain.entity.recipe.RecipeEntity) = repository.addRecipe(recipe)

}
