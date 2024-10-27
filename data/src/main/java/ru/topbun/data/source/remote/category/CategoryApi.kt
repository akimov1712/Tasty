package ru.topbun.tasty.data.source.remote.category

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.topbun.tasty.data.source.remote.category.dto.CategoryDTO

interface CategoryApi {

    @GET("/category")
    suspend fun getCategories(): Response<List<CategoryDTO>>

    @GET("/category/{id}")
    suspend fun getCategoriesWithId(@Path("id") id: Int): Response<CategoryDTO>

}