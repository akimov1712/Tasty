package ru.topbun.recipes

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.navigation.main.MainScreenNavigator
import ru.topbun.navigation.main.MainScreenProvider
import ru.topbun.recipes.RecipeState.RecipeScreenState
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AnimateTitle
import ru.topbun.ui.components.ErrorComponent
import ru.topbun.ui.components.RecipeItem
import ru.topbun.ui.components.SearchTextField
import ru.topbun.ui.components.TabRow

data object RecipesScreen : Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 24.dp, end = 12.dp)
        ) {
            val context = LocalContext.current
            val mainNavigator = koinScreenModel<MainScreenNavigator>()
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
            RecipeList(viewModel){
                val detailRecipeScreen =  ScreenRegistry.get(MainScreenProvider.DetailRecipe(it.id))
                mainNavigator.pushScreen(detailRecipeScreen)
            }
            when(val screenState = state.favoriteState){
                is RecipeState.FavoriteScreenState.Error -> {
                    LaunchedEffect(state.favoriteState) {
                        Toast.makeText(context, screenState.msg, Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {}
            }
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = state.lazyListState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.recipes){
                RecipeItem(
                    recipe = it,
                    onClickFavorite = {
                        viewModel.changeFavorite(it.id, !it.isFavorite)
                    },
                    onClickRecipe = onClickRecipe
                )
            }
            item(span = { GridItemSpan(2) }) {
                PaginationLoader(viewModel = viewModel, state = state)
            }
            val screenState = state.recipeState
            if (screenState is RecipeScreenState.Error) {
                item(span = { GridItemSpan(2) }) {
                    ErrorComponent(text = screenState.msg) {
                        viewModel.loadRecipes()
                    }
                }
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
