package ru.topbun.tasty.domain.repository.recipe

import ru.topbun.tasty.domain.entity.recipe.RecipeEntity

interface RecipeRepository {

    suspend fun addRecipe(recipe: RecipeEntity): RecipeEntity
    suspend fun getRecipes(offset: Int, limit: Int): List<RecipeEntity>
    suspend fun getMyRecipes(offset: Int, limit: Int): List<RecipeEntity>
    suspend fun getRecipesWithId(id: Int): RecipeEntity
    suspend fun getRecipesWithCategory(categoryId: Int, offset: Int, limit: Int): List<RecipeEntity>
    suspend fun uploadImage(image: ByteArray): String

}