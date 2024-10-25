package ru.topbun.tasty.domain.useCases.category

import ru.topbun.tasty.domain.repository.category.CategoryRepository

class GetCategoriesUseCase(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke() = repository.getCategories()

}