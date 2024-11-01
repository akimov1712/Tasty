package ru.topbun.category

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.category.CategoryState.*
import ru.topbun.category.CategoryState.CategoryScreenState.*
import ru.topbun.domain.useCases.category.GetCategoriesUseCase

class CategoryViewModel(
    private val getCategoryUseCase: GetCategoriesUseCase,
): ScreenModelState<CategoryState>(CategoryState()) {

    private var searchJob: Job? = null

    init {
        loadCategories()
    }

    fun loadCategories(){
        searchJob?.cancel()
        searchJob = screenModelScope.launch {
            wrapperException({
                updateState { copy(categoryState = Loading) }
                val result = getCategoryUseCase(
                    q = stateValue.searchQuery,
                    offset = stateValue.categories.size,
                )
                if (result.isEmpty()) updateState { copy(isEndList = true) }
                updateState { copy(categories = categories + result) }
                updateState { copy(categoryState = Success) }
            }) {
                updateState { copy(categoryState = CategoryScreenState.Error(it)) }
            }
        }
    }

    fun changeQuery(q: String){
        clearCategories()
        updateState { copy(searchQuery = q) }
    }

    private fun clearCategories() = updateState {
        copy(
            categories = emptyList(),
            isEndList = false,
            categoryState = Initial
        )
    }

}