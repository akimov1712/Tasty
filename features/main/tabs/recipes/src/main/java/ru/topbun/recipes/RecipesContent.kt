package ru.topbun.recipes

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.recipes.RecipeState.RecipeScreenState
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AnimateTitle
import ru.topbun.ui.components.RecipeItem
import ru.topbun.ui.components.SearchTextField
import ru.topbun.ui.components.TabRow

data class RecipesScreen(
    val onOpenDetailRecipe: (RecipeEntity) -> Unit
) : Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 24.dp, end = 12.dp)
        ) {
            val viewModel = koinScreenModel<RecipeViewModel>()
            val state by viewModel.state.collectAsState()
            AnimateTitle(
                text = "Рецепты",
                state.lazyListState.firstVisibleItemIndex == 0
            )

            SearchTextField(
                value = state.searchQuery,
                onValueChange = { if (it.trim().length <= 64) viewModel.changeQuery(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            TabRow(
                items = RecipeTabs.entries.map { it.title },
                selectedIndex = state.selectedTabIndex
            ) { viewModel.changeTab(it) }
            Spacer(modifier = Modifier.height(10.dp))
            RecipeList(viewModel, onOpenDetailRecipe)
        }
    }

}

@Composable
private fun ColumnScope.RecipeList(viewModel: RecipeViewModel, onClickRecipe: (RecipeEntity) -> Unit) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (state.recipes.isEmpty()) {
            if (state.recipeState == RecipeScreenState.Loading) {
                CircularProgressIndicator(color = Colors.BLUE)
            }
            if (state.recipeState == RecipeScreenState.Success) {
                Text(text = "Пусто", style = Typography.Title3)
            }
        }

        LazyColumn(
            state = state.lazyListState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(state.recipes.chunked(2)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    RecipeItem(it.first(), onClickRecipe)
                    RecipeItem(it.last(), onClickRecipe)
                }
            }
            item {
                PaginationLoader(viewModel = viewModel, state = state)
            }
        }
    }
}

@Composable
private fun PaginationLoader(viewModel: RecipeViewModel, state: RecipeState) {
    if (!state.isEndList) {
        val screenState = state.recipeState
        if (listOf(RecipeScreenState.Success, RecipeScreenState.Initial).contains(screenState)) {
            LaunchedEffect(state.recipes.toString()) {
                viewModel.loadRecipes()
            }
        } else if (screenState == RecipeScreenState.Loading && state.recipes.isNotEmpty()) {
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
