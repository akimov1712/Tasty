package ru.topbun.tasty.data.repository

import ru.topbun.tasty.data.mapper.toEntity
import ru.topbun.tasty.data.source.local.dataStore.Settings
import ru.topbun.tasty.data.source.remote.category.CategoryApi
import ru.topbun.tasty.domain.entity.category.CategoryEntity
import ru.topbun.tasty.domain.repository.category.CategoryRepository
import ru.topbun.tasty.utills.codeResultWrapper
import ru.topbun.tasty.utills.exceptionWrapper

class CategoryRepositoryImpl(
    private val api: CategoryApi,
    private val settings: Settings
): CategoryRepository {

    override suspend fun getCategories(): List<CategoryEntity> = exceptionWrapper{
        api.getCategories().codeResultWrapper().toEntity()
    }

    override suspend fun getCategoriesWithId(id: Int): CategoryEntity = exceptionWrapper{
        api.getCategoriesWithId(id).codeResultWrapper().toEntity()
    }
}