package ru.topbun.category

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import ru.topbun.domain.entity.category.CategoryEntity

data class CategoryState(
    val searchQuery: String = "",
    val lazyListState: LazyGridState = LazyGridState(),
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
