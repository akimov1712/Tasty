package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.entity.recipe.RecipeTabs
import ru.topbun.domain.repository.recipe.RecipeRepository

class SaveRecipeUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(recipe: RecipeEntity) = repository.saveRecipe(recipe)

}
