package ru.topbun.data.source.network.recipe

import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import ru.topbun.data.source.network.ApiFactory
import ru.topbun.data.source.network.recipe.dto.FavoriteReceive
import ru.topbun.data.source.network.recipe.dto.GetRecipeReceive
import ru.topbun.data.source.network.recipe.dto.RecipeDTO
import ru.topbun.data.source.network.token

class RecipeApi(private val api: ApiFactory) {

    suspend fun addRecipe(recipe: RecipeDTO, token: String) = api.client.post("/recipe/add"){
        setBody(recipe)
        token(token)
    }

    suspend fun getRecipes(data: GetRecipeReceive, token: String) = api.client.post("/recipe"){
        token(token)
        setBody(data)
    }

    suspend fun getMyRecipes(data: GetRecipeReceive, token: String) = api.client.post("/recipe/my"){
        setBody(data)
        token(token)
    }

    suspend fun getRecipesWithId(id: Int, token: String) = api.client.post("/recipe/$id"){
        token(token)
    }

    suspend fun getRecipesWithCategory(categoryId: Int, data: GetRecipeReceive, token: String) = api.client.post("/recipe/category/$categoryId"){
        setBody(data)
        token(token)
    }

    suspend fun changeFavorite(recipeId: Int, favoriteReceive: FavoriteReceive, token: String) = api.client.post("/favorite/$recipeId"){
        token(token)
        setBody(favoriteReceive)
    }

    suspend fun getFavoritesRecipe(token: String) = api.client.get("/favorite"){
        token(token)
    }

    suspend fun uploadImage(image: ByteArray) = api.client.submitFormWithBinaryData(
        url = "/upload",
        formData = formData {
            append("image", InputProvider {
                buildPacket {
                    writeFully(image)
                }
            }, Headers.build {
                append(HttpHeaders.ContentType, "image/jpeg")
                append(HttpHeaders.ContentDisposition, "filename=\"photo.jpg\"")
            })
        }
    )

}