package ru.topbun.tasty.domain.useCases.category

import ru.topbun.tasty.domain.repository.category.CategoryRepository

class GetCategoriesWithIdUseCase(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(id: Int) = repository.getCategoriesWithId(id)

}