package ru.topbun.tasty.data.repository

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.topbun.android.codeResultWrapper
import ru.topbun.android.exceptionWrapper
import ru.topbun.data.mapper.toDTO
import ru.topbun.data.mapper.toEntity
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.repository.recipe.RecipeRepository
import ru.topbun.tasty.data.source.remote.recipe.RecipeApi

class RecipeRepositoryImpl(
    private val api: RecipeApi,
): RecipeRepository {

    override suspend fun addRecipe(recipe: RecipeEntity): RecipeEntity =
        ru.topbun.android.exceptionWrapper {
            api.addRecipe(recipe.toDTO()).codeResultWrapper().toEntity()
        }

    override suspend fun getRecipes(offset: Int, limit: Int): List<RecipeEntity> =
        ru.topbun.android.exceptionWrapper {
            api.getRecipes(offset, limit).codeResultWrapper().toEntity()
        }

    override suspend fun getMyRecipes(offset: Int, limit: Int): List<RecipeEntity> =
        ru.topbun.android.exceptionWrapper {
            api.getMyRecipes(offset, limit).codeResultWrapper().toEntity()
        }

    override suspend fun getRecipesWithId(id: Int): RecipeEntity =
        ru.topbun.android.exceptionWrapper {
            api.getRecipesWithId(id).codeResultWrapper().toEntity()
        }

    override suspend fun getRecipesWithCategory(categoryId: Int, offset: Int, limit: Int): List<RecipeEntity> =
        ru.topbun.android.exceptionWrapper {
            api.getRecipesWithCategory(categoryId, offset, limit).codeResultWrapper().toEntity()
        }

    override suspend fun uploadImage(image: ByteArray): String =
        ru.topbun.android.exceptionWrapper {
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), image)
            val imageFormData = MultipartBody.Part.createFormData("image", null, requestFile)
            api.uploadImage(imageFormData).codeResultWrapper().url
        }
}