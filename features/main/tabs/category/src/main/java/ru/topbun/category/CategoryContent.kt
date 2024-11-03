package ru.topbun.category

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import ru.topbun.category.CategoryState.CategoryScreenState
import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AnimateTitle
import ru.topbun.ui.components.CategoryItem
import ru.topbun.ui.components.ImagePlaceholder
import ru.topbun.ui.components.SearchTextField

data object CategoryScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 24.dp, end = 12.dp)
        ) {
            val viewModel = koinScreenModel<CategoryViewModel>()
            val state by viewModel.state.collectAsState()
            AnimateTitle(
                text = "Категории",
                isTitleVisible = state.lazyListState.firstVisibleItemIndex == 0
            )

            SearchTextField(
                value = state.searchQuery,
                onValueChange = { if (it.trim().length <= 64) viewModel.changeQuery(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CategoryList(viewModel)
        }
    }
}

@Composable
private fun ColumnScope.CategoryList(viewModel: CategoryViewModel) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (state.categories.isEmpty()) {
            if (state.categoryState == CategoryScreenState.Loading) {
                CircularProgressIndicator(color = Colors.BLUE)
            }
            if (state.categoryState == CategoryScreenState.Success) {
                Text(text = "Пусто", style = Typography.Title3)
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = state.lazyListState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(state.categories) {
                CategoryItem(it)
            }
            item(span = { GridItemSpan(2) }) {
                PaginationLoader(viewModel = viewModel, state = state)
            }
        }
    }
}

@Composable
private fun PaginationLoader(viewModel: CategoryViewModel, state: CategoryState) {
    if (!state.isEndList) {
        val screenState = state.categoryState
        if (listOf(CategoryScreenState.Success, CategoryScreenState.Initial).contains(screenState)) {
            LaunchedEffect(state.categories) {
                viewModel.loadCategories()
            }
        } else if (screenState == CategoryScreenState.Loading && state.categories.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Colors.BLUE)
            }
        }
    }
}