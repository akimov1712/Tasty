package ru.topbun.tasty.data.repository

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.topbun.tasty.data.mapper.toDTO
import ru.topbun.tasty.data.mapper.toEntity
import ru.topbun.tasty.data.source.local.dataStore.Settings
import ru.topbun.tasty.data.source.remote.auth.AuthApi
import ru.topbun.tasty.data.source.remote.recipe.RecipeApi
import ru.topbun.tasty.domain.entity.recipe.RecipeEntity
import ru.topbun.tasty.domain.repository.recipe.RecipeRepository
import ru.topbun.tasty.utills.codeResultWrapper
import ru.topbun.tasty.utills.exceptionWrapper

class RecipeRepositoryImpl(
    private val api: RecipeApi,
): RecipeRepository {

    override suspend fun addRecipe(recipe: RecipeEntity): RecipeEntity = exceptionWrapper{
        api.addRecipe(recipe.toDTO()).codeResultWrapper().toEntity()
    }

    override suspend fun getRecipes(offset: Int, limit: Int): List<RecipeEntity> = exceptionWrapper{
        api.getRecipes(offset, limit).codeResultWrapper().toEntity()
    }

    override suspend fun getMyRecipes(offset: Int, limit: Int): List<RecipeEntity> = exceptionWrapper{
        api.getMyRecipes(offset, limit).codeResultWrapper().toEntity()
    }

    override suspend fun getRecipesWithId(id: Int): RecipeEntity = exceptionWrapper{
        api.getRecipesWithId(id).codeResultWrapper().toEntity()
    }

    override suspend fun getRecipesWithCategory(categoryId: Int, offset: Int, limit: Int): List<RecipeEntity> = exceptionWrapper{
        api.getRecipesWithCategory(categoryId, offset, limit).codeResultWrapper().toEntity()
    }

    override suspend fun uploadImage(image: ByteArray): String = exceptionWrapper{
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), image)
        val imageFormData = MultipartBody.Part.createFormData("image", null, requestFile)
        api.uploadImage(imageFormData).codeResultWrapper().url
    }
}