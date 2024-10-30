package ru.topbun.data.repository

import io.ktor.client.call.body
import ru.topbun.data.codeResultWrapper
import ru.topbun.data.exceptionWrapper
import ru.topbun.data.mapper.toDTO
import ru.topbun.data.mapper.toEntity
import ru.topbun.data.source.local.dataStore.Settings
import ru.topbun.data.source.local.dataStore.getToken
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.repository.recipe.RecipeRepository
import ru.topbun.data.source.network.recipe.RecipeApi
import ru.topbun.data.source.network.recipe.dto.GetRecipeReceive
import ru.topbun.data.source.network.recipe.dto.GetRecipeWithoutQueryReceive
import ru.topbun.data.source.network.recipe.dto.RecipeDTO

class RecipeRepositoryImpl(
    private val api: RecipeApi,
    private val settings: Settings,
): RecipeRepository {

    override suspend fun addRecipe(recipe: RecipeEntity): RecipeEntity = exceptionWrapper {
            api.addRecipe(
                recipe = recipe.toDTO(),
                token = settings.getToken()
            ).codeResultWrapper().body<RecipeDTO>().toEntity()
        }

    override suspend fun getRecipes(q: String, offset: Int, limit: Int, isMyRecipe: Boolean): List<RecipeEntity> = exceptionWrapper {
            val data = GetRecipeReceive(q, offset, limit)
            val result = if (isMyRecipe) api.getMyRecipes(data, settings.getToken())
                        else api.getRecipes(data)
            result.codeResultWrapper().body<List<RecipeDTO>>().toEntity()
        }

    override suspend fun getRecipesWithId(id: Int): RecipeEntity = exceptionWrapper {
            api.getRecipesWithId(id).codeResultWrapper().body<RecipeDTO>().toEntity()
        }

    override suspend fun getRecipesWithCategory(categoryId: Int, offset: Int, limit: Int): List<RecipeEntity> = exceptionWrapper {
            val data = GetRecipeWithoutQueryReceive(offset, limit)
            api.getRecipesWithCategory(categoryId, data).codeResultWrapper().body<List<RecipeDTO>>().toEntity()
        }

    override suspend fun uploadImage(image: ByteArray): String = exceptionWrapper {
            api.uploadImage(image).body()
        }
}