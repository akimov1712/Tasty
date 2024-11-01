package ru.topbun.domain.repository.category

import ru.topbun.domain.entity.category.CategoryEntity

interface CategoryRepository {

    suspend fun getCategories(q: String, offset: Int, limit: Int): List<CategoryEntity>
    suspend fun getCategoriesWithId(id: Int): CategoryEntity

}