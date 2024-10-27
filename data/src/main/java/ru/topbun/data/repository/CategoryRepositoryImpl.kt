package ru.topbun.tasty.data.repository

import ru.topbun.core.utills.codeResultWrapper
import ru.topbun.core.utills.exceptionWrapper
import ru.topbun.data.mapper.toEntity
import ru.topbun.tasty.data.source.remote.category.CategoryApi
import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.domain.repository.category.CategoryRepository

class CategoryRepositoryImpl(
    private val api: CategoryApi,
): CategoryRepository {

    override suspend fun getCategories(): List<CategoryEntity> = exceptionWrapper{
        api.getCategories().codeResultWrapper().toEntity()
    }

    override suspend fun getCategoriesWithId(id: Int): CategoryEntity = exceptionWrapper{
        api.getCategoriesWithId(id).codeResultWrapper().toEntity()
    }
}