package ru.topbun.domain.repository.category

import ru.topbun.domain.entity.category.CategoryEntity

interface CategoryRepository {

    suspend fun getCategories(): List<ru.topbun.domain.entity.category.CategoryEntity>
    suspend fun getCategoriesWithId(id: Int): ru.topbun.domain.entity.category.CategoryEntity

}