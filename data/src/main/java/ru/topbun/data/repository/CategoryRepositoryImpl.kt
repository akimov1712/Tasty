package ru.topbun.data.repository

import io.ktor.client.call.body
import ru.topbun.data.codeResultWrapper
import ru.topbun.data.exceptionWrapper
import ru.topbun.data.mapper.toEntity
import ru.topbun.data.source.network.category.CategoryApi
import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.domain.repository.category.CategoryRepository
import ru.topbun.tasty.data.source.remote.category.dto.CategoryDTO

class CategoryRepositoryImpl(
    private val api: CategoryApi,
): CategoryRepository {

    override suspend fun getCategories(): List<CategoryEntity> = exceptionWrapper {
        api.getCategories().codeResultWrapper().body<List<CategoryDTO>>().toEntity()
    }

    override suspend fun getCategoriesWithId(id: Int): CategoryEntity = exceptionWrapper {
        api.getCategoriesWithId(id).codeResultWrapper().body<CategoryDTO>().toEntity()
    }
}