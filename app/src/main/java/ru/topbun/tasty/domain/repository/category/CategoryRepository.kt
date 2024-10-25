package ru.topbun.tasty.domain.repository.category

import ru.topbun.tasty.domain.entity.category.CategoryEntity

interface CategoryRepository {

    suspend fun getCategories(): List<CategoryEntity>
    suspend fun getCategoriesWithId(id: Int): CategoryEntity

}