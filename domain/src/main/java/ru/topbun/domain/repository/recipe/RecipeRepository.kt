package ru.topbun.domain.repository.recipe

import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.entity.recipe.RecipeTabs

interface RecipeRepository {

    suspend fun addRecipe(recipe: RecipeEntity): RecipeEntity
    suspend fun getRecipes(q: String, offset: Int, limit: Int, type: RecipeTabs): List<RecipeEntity>
    suspend fun getRecipesWithId(id: Int, fromCache: Boolean): RecipeEntity
    suspend fun getRecipesWithCategory(categoryId: Int, offset: Int, limit: Int): List<RecipeEntity>
    suspend fun uploadImage(image: ByteArray): String
    suspend fun changeFavorite(recipeId: Int, isFavorite: Boolean): Boolean
    suspend fun getFavoritesRecipes(): List<RecipeEntity>
    suspend fun deleteRecipe(id: Int, fromCache: Boolean)
    suspend fun saveRecipe(recipe: RecipeEntity)
    suspend fun recipeLocalExists(id: Int): Boolean

}