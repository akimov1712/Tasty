package ru.topbun.favorite

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.favorite.FavoriteState.FavoriteScreenState
import ru.topbun.favorite.FavoriteState.FavoriteScreenState.*
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AnimateTitle
import ru.topbun.ui.components.RecipeItem
import ru.topbun.ui.components.SearchTextField
import ru.topbun.ui.components.TabRow

data object FavoriteScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 24.dp, end = 12.dp)
        ) {
            val viewModel = koinScreenModel<FavoriteViewModel>()
            val state by viewModel.state.collectAsState()
            AnimateTitle(
                text = "Избранные",
                state.lazyListState.firstVisibleItemIndex == 0
            )
            Spacer(modifier = Modifier.height(10.dp))
            FavoriteList(viewModel) {}
        }
    }
}

@Composable
private fun ColumnScope.FavoriteList(viewModel: FavoriteViewModel, onClickRecipe: (RecipeEntity) -> Unit) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (state.recipes.isEmpty()) {
            if (state.recipeState == Loading) {
                CircularProgressIndicator(color = Colors.BLUE)
            }
            if (state.recipeState == Success) {
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
                RecipeItem(it, onClickRecipe)
            }
        }
    }
}