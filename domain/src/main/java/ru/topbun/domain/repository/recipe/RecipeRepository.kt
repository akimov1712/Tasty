package ru.topbun.domain.repository.recipe

import ru.topbun.domain.entity.recipe.RecipeEntity

interface RecipeRepository {

    suspend fun addRecipe(recipe: RecipeEntity): RecipeEntity
    suspend fun getRecipes(q: String, offset: Int, limit: Int, isMyRecipe: Boolean): List<RecipeEntity>
    suspend fun getRecipesWithId(id: Int): RecipeEntity
    suspend fun getRecipesWithCategory(categoryId: Int, offset: Int, limit: Int): List<RecipeEntity>
    suspend fun uploadImage(image: ByteArray): String
    suspend fun changeFavorite(recipeId: Int, isFavorite: Boolean): Boolean
    suspend fun getFavoritesRecipes(): List<RecipeEntity>
    suspend fun deleteRecipe(id: Int)

}