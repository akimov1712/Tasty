package ru.topbun.data.source.network.category

import io.ktor.client.request.get
import ru.topbun.data.source.network.ApiFactory

class CategoryApi(private val api: ApiFactory) {

    suspend fun getCategories() = api.client.get("/category")

    suspend fun getCategoriesWithId(id: Int) = api.client.get("/category/$id")

}