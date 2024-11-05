package ru.topbun.data.repository

import io.ktor.client.call.body
import ru.topbun.data.codeResultWrapper
import ru.topbun.data.exceptionWrapper
import ru.topbun.data.mapper.toEntity
import ru.topbun.data.source.network.category.CategoryApi
import ru.topbun.data.source.network.category.dto.CategoryResponse
import ru.topbun.data.source.network.category.dto.GetCategoryReceive
import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.domain.repository.category.CategoryRepository
import ru.topbun.tasty.data.source.remote.category.dto.CategoryDTO

class CategoryRepositoryImpl(
    private val api: CategoryApi,
): CategoryRepository {

    override suspend fun getCategories(q: String, offset: Int, limit: Int): List<CategoryEntity> = exceptionWrapper {
        val data = GetCategoryReceive(q.trim(), offset, limit)
        api.getCategories(data).codeResultWrapper().body<CategoryResponse>().categories.toEntity()
    }

    override suspend fun getCategoriesWithId(id: Int): CategoryEntity = exceptionWrapper {
        api.getCategoriesWithId(id).codeResultWrapper().body<CategoryDTO>().toEntity()
    }
}