package ru.topbun.data.source.network.category

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.topbun.data.source.network.ApiFactory
import ru.topbun.data.source.network.category.dto.GetCategoryReceive

class CategoryApi(private val api: ApiFactory) {

    suspend fun getCategories(
        data: GetCategoryReceive
    ) = api.client.post("/category"){
        setBody(data)
    }

    suspend fun getCategoriesWithId(id: Int) = api.client.get("/category/$id")

}