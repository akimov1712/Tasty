package ru.topbun.domain.useCases.category

import ru.topbun.domain.repository.category.CategoryRepository

class GetCategoriesUseCase(
    private val repository: ru.topbun.domain.repository.category.CategoryRepository
) {

    suspend operator fun invoke() = repository.getCategories()

}