package ru.topbun.domain.repository.recipe

import ru.topbun.domain.entity.recipe.RecipeEntity

interface RecipeRepository {

    suspend fun addRecipe(recipe: ru.topbun.domain.entity.recipe.RecipeEntity): ru.topbun.domain.entity.recipe.RecipeEntity
    suspend fun getRecipes(offset: Int, limit: Int): List<ru.topbun.domain.entity.recipe.RecipeEntity>
    suspend fun getMyRecipes(offset: Int, limit: Int): List<ru.topbun.domain.entity.recipe.RecipeEntity>
    suspend fun getRecipesWithId(id: Int): ru.topbun.domain.entity.recipe.RecipeEntity
    suspend fun getRecipesWithCategory(categoryId: Int, offset: Int, limit: Int): List<ru.topbun.domain.entity.recipe.RecipeEntity>
    suspend fun uploadImage(image: ByteArray): String

}