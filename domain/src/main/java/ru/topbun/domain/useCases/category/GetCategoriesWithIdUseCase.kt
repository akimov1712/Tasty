package ru.topbun.domain.useCases.category

import ru.topbun.domain.repository.category.CategoryRepository

class GetCategoriesWithIdUseCase(
    private val repository: ru.topbun.domain.repository.category.CategoryRepository
) {

    suspend operator fun invoke(id: Int) = repository.getCategoriesWithId(id)

}