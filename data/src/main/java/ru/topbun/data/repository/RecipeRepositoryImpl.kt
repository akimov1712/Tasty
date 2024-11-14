package ru.topbun.data.repository

import io.ktor.client.call.body
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import ru.topbun.android.ClientException
import ru.topbun.data.BuildConfig
import ru.topbun.data.codeResultWrapper
import ru.topbun.data.exceptionWrapper
import ru.topbun.data.mapper.toDBO
import ru.topbun.data.mapper.toDTO
import ru.topbun.data.mapper.toEntity
import ru.topbun.data.source.local.dataStore.AppSettings
import ru.topbun.data.source.local.dataStore.Settings
import ru.topbun.data.source.local.dataStore.getToken
import ru.topbun.data.source.local.database.dao.RecipeDao
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.repository.recipe.RecipeRepository
import ru.topbun.data.source.network.recipe.RecipeApi
import ru.topbun.data.source.network.recipe.dto.FavoriteDTO
import ru.topbun.data.source.network.recipe.dto.FavoriteReceive
import ru.topbun.data.source.network.recipe.dto.GetRecipeReceive
import ru.topbun.data.source.network.recipe.dto.RecipeDTO
import ru.topbun.domain.entity.recipe.RecipeTabs
import ru.topbun.domain.entity.recipe.RecipeTabs.*
import ru.topbun.tasty.data.source.remote.recipe.dto.UploadDTO

class RecipeRepositoryImpl(
    private val api: RecipeApi,
    private val settings: Settings,
    private val dao: RecipeDao
): RecipeRepository {

    fun String.resolve() = "${BuildConfig.BASE_URL}/$this"

    override suspend fun addRecipe(recipe: RecipeEntity): RecipeEntity = exceptionWrapper {
        val newRecipe = recipe.copy(
            image = recipe.image?.resolve(),
            steps = recipe.steps.map { it.copy(preview = it.preview?.resolve()) }
        )
        api.addRecipe(
            recipe = newRecipe.toDTO(),
            token = settings.getToken()
        ).codeResultWrapper().body<RecipeDTO>().toEntity()
    }

    override suspend fun getRecipes(q: String, offset: Int, limit: Int, type: RecipeTabs): List<RecipeEntity> = exceptionWrapper {
            val data = GetRecipeReceive(q.trim(), offset, limit)
            when(type){
                AllRecipes -> {
                    val token = settings.data
                        .map { it[AppSettings.KEY_TOKEN] }
                        .firstOrNull() ?: ""
                    api.getRecipes(data, token).codeResultWrapper().body<List<RecipeDTO>>().toEntity()
                }
                MyRecipes -> { api.getMyRecipes(data, settings.getToken()).codeResultWrapper().body<List<RecipeDTO>>().toEntity() }
                SaveRecipes -> { dao.getRecipeList(q, offset, limit).map { it.toEntity() } }
            }
        }

    override suspend fun getRecipesWithId(id: Int, fromCache: Boolean): RecipeEntity = exceptionWrapper {
        if (fromCache){
            dao.getRecipeWithId(id)?.toEntity() ?: throw ClientException("Рецепт был удален из вашего устройства")
        } else {
            val token = settings.data
                .map { it[AppSettings.KEY_TOKEN] }
                .firstOrNull() ?: ""
            api.getRecipesWithId(id, token).codeResultWrapper().body<RecipeDTO>().toEntity()
        }
    }

    override suspend fun getRecipesWithCategory(categoryId: Int, offset: Int, limit: Int): List<RecipeEntity> = exceptionWrapper {
            val data = GetRecipeReceive(q= "", offset, limit)
            val token = settings.data
                .map { it[AppSettings.KEY_TOKEN] }
                .firstOrNull() ?: ""
            api.getRecipesWithCategory(categoryId, data, token).codeResultWrapper().body<List<RecipeDTO>>().toEntity()
        }

    override suspend fun uploadImage(image: ByteArray): String = exceptionWrapper {
            api.uploadImage(image).body<UploadDTO>().url
        }

    override suspend fun changeFavorite(recipeId: Int, favorite: Boolean): Boolean = exceptionWrapper {
        api.changeFavorite(
            recipeId = recipeId,
            favoriteReceive = FavoriteReceive(favorite),
            token = settings.getToken()
        ).codeResultWrapper().body<FavoriteDTO>().isFavorite
    }

    override suspend fun getFavoritesRecipes(): List<RecipeEntity> = exceptionWrapper{
        api.getFavoritesRecipe(settings.getToken()).codeResultWrapper().body<List<RecipeDTO>>().toEntity()
    }

    override suspend fun deleteRecipe(id: Int, fromCache: Boolean): Unit = exceptionWrapper {
        if (!fromCache) api.deleteRecipe(id, settings.getToken()).codeResultWrapper()
        dao.deleteRecipe(id)

    }

    override suspend fun saveRecipe(recipe: RecipeEntity) {
        dao.insertRecipe(recipe.toDBO())
    }

    override suspend fun recipeLocalExists(id: Int): Boolean {
        return dao.isExists(id)
    }
}