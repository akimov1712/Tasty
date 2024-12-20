package ru.topbun.domain.useCases.recipe

import ru.topbun.domain.entity.recipe.RecipeTabs
import ru.topbun.domain.repository.recipe.RecipeRepository

class GetRecipeUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(q: String, offset: Int = 0, limit: Int = 20, type: RecipeTabs = RecipeTabs.MyRecipes) = repository.getRecipes(q, offset, limit, type)

}
