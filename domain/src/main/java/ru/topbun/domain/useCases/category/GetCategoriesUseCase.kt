package ru.topbun.domain.useCases.category

import ru.topbun.domain.repository.category.CategoryRepository

class GetCategoriesUseCase(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(
        q: String = "",
        offset: Int = 0,
        limit: Int = 20,
    ) = repository.getCategories(q, offset, limit)

}