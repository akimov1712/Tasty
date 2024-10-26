package ru.topbun.tasty.data.source.remote.recipe

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ru.topbun.tasty.data.source.remote.recipe.dto.RecipeDTO
import ru.topbun.tasty.data.source.remote.recipe.dto.UploadDTO
import ru.topbun.tasty.domain.entity.recipe.RecipeEntity

interface RecipeApi {

    @POST("/recipe")
    suspend fun addRecipe(@Body recipeDTO: RecipeDTO, token: String): Response<RecipeDTO>

    @GET("/recipe")
    suspend fun getRecipes(@Body offset: Int, @Body limit: Int): Response<List<RecipeDTO>>

    @GET("/recipe/my")
    suspend fun getMyRecipes(@Body offset: Int, @Body limit: Int): Response<List<RecipeDTO>>

    @GET("/recipe/{id}")
    suspend fun getRecipesWithId(@Path("id") id: Int): Response<RecipeDTO>

    @GET("/recipe/category/{id}")
    suspend fun getRecipesWithCategory(@Path("id") categoryId: Int, @Body offset: Int, @Body limit: Int): Response<List<RecipeDTO>>

    @Multipart
    @POST("/upload")
    suspend fun uploadImage(@Part("image") image: MultipartBody.Part): Response<UploadDTO>

}