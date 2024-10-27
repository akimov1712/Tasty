package ru.topbun.tasty.data.repository

import ru.topbun.android.codeResultWrapper
import ru.topbun.android.exceptionWrapper
import ru.topbun.data.mapper.toEntity
import ru.topbun.tasty.data.source.remote.category.CategoryApi
import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.domain.repository.category.CategoryRepository

class CategoryRepositoryImpl(
    private val api: CategoryApi,
): CategoryRepository {

    override suspend fun getCategories(): List<CategoryEntity> =
        ru.topbun.android.exceptionWrapper {
            api.getCategories().codeResultWrapper().toEntity()
        }

    override suspend fun getCategoriesWithId(id: Int): CategoryEntity =
        ru.topbun.android.exceptionWrapper {
            api.getCategoriesWithId(id).codeResultWrapper().toEntity()
        }
}