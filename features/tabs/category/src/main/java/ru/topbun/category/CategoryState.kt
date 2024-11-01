package ru.topbun.category

import androidx.compose.foundation.lazy.LazyListState
import ru.topbun.domain.entity.category.CategoryEntity

data class CategoryState(
    val searchQuery: String = "",
    val lazyListState: LazyListState = LazyListState(),
    val categories: List<CategoryEntity> = listOf(),
    val isEndList: Boolean = false,
    val categoryState: CategoryScreenState = CategoryScreenState.Initial
){

    sealed interface CategoryScreenState{
        data object Initial: CategoryScreenState
        data object Loading: CategoryScreenState
        data class Error(val msg: String): CategoryScreenState
        data object Success: CategoryScreenState
    }

}
