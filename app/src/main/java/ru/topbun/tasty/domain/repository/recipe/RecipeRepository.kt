package ru.topbun.tasty.domain.repository.recipe

import ru.topbun.tasty.domain.entity.recipe.RecipeEntity
import ru.topbun.tasty.domain.entity.recipe.UploadEntity

interface RecipeRepository {

    suspend fun getRecipes(offset: Int, limit: Int): List<RecipeEntity>
    suspend fun getRecipesWithId(id: Int): List<RecipeEntity>
    suspend fun getRecipesWithCategory(categoryId: Int): List<RecipeEntity>
    suspend fun uploadImage(image: ByteArray): UploadEntity

}