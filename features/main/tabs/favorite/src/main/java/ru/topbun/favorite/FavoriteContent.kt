package ru.topbun.favorite

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
import androidx.compose.material3.MaterialTheme
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
import ru.topbun.favorite.FavoriteState.FavoriteScreenState.*
import ru.topbun.navigation.main.MainScreenNavigator
import ru.topbun.navigation.main.MainScreenProvider
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AdaptiveInlineBanner
import ru.topbun.ui.components.AnimateTitle
import ru.topbun.ui.components.ErrorComponent
import ru.topbun.ui.components.RecipeItem

data object FavoriteScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 24.dp, end = 12.dp)
        ) {
            val mainNavigator = koinScreenModel<MainScreenNavigator>()
            val viewModel = koinScreenModel<FavoriteViewModel>()
            val state by viewModel.state.collectAsState()
            LaunchedEffect(Unit) {
                viewModel.loadRecipes()
            }
            AnimateTitle(
                text = "Избранные",
                state.lazyListState.firstVisibleItemIndex == 0
            )
            Spacer(modifier = Modifier.height(10.dp))
            FavoriteList(viewModel) {
                val detailRecipeScreen =  ScreenRegistry.get(MainScreenProvider.DetailRecipe(it.id, false))
                mainNavigator.pushScreen(detailRecipeScreen)
            }
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
        val context = LocalContext.current
        when(val screenState = state.favoriteState){
            is Error -> {
                LaunchedEffect(state.favoriteState) {
                    Toast.makeText(context, screenState.msg, Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }

        when(val recipeScreenState = state.recipeState){
            is FavoriteState.RecipeScreenState.Error -> ErrorComponent(text = recipeScreenState.msg) {
                viewModel.loadRecipes()
            }
            else -> {}
        }

        if (state.recipes.isEmpty()) {
            if (state.recipeState == FavoriteState.RecipeScreenState.Loading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            if (state.recipeState == FavoriteState.RecipeScreenState.Success) {
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
            state.recipes.forEachIndexed { index, recipe ->
                if (index != 0 && index % 20 == 0){
                    item(span = { GridItemSpan(2) }) {
                        AdaptiveInlineBanner()
                    }
                }
                item {
                    RecipeItem(recipe, {viewModel.changeFavorite(recipe.id, !recipe.isFavorite)}, onClickRecipe)
                }
            }
        }
    }
}