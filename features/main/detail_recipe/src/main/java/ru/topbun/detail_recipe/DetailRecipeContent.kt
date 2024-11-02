package ru.topbun.detail_recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.topbun.common.convertCookingTime
import ru.topbun.common.improveImageQuality
import ru.topbun.detail_recipe.tabs.DetailRecipeTabs
import ru.topbun.detail_recipe.tabs.GeneralTabsScreen
import ru.topbun.detail_recipe.tabs.StepTabsScreen
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.ui.Colors
import ru.topbun.ui.R
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AppAsyncImage
import ru.topbun.ui.components.AppIconButton
import ru.topbun.ui.util.noRippleClickable

data class DetailRecipeScreen(val recipe: RecipeEntity) : Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val viewModel = koinScreenModel<DetailRecipeViewModel>()
            val state by viewModel.state.collectAsState()
            Header(recipe)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = -32.dp)
            ) {
                Table(recipe)
                Spacer(modifier = Modifier.height(20.dp))
                TabRow(
                    tabs = state.tabs.map { it.title },
                    selectedIndex = state.selectedTabIndex
                ) { viewModel.changeTabIndex(it) }
                when (state.tabs.elementAt(state.selectedTabIndex)) {
                    DetailRecipeTabs.GENERAL -> GeneralTabsScreen(recipe = recipe)
                    DetailRecipeTabs.STEP_BY_STEP -> StepTabsScreen(recipe = recipe)
                }
            }
        }
    }
}

@Composable
private fun TabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onSelectedIndex: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, item ->
            val color = if (selectedIndex == index) Colors.BLUE else Colors.BLUE_GRAY
            Text(
                modifier = Modifier
                    .noRippleClickable {
                        onSelectedIndex(index)
                    }
                    .padding(horizontal = 20.dp),
                text = item,
                style = Typography.Tabs2,
                color = color
            )
        }
    }
}

@Composable
fun Table(recipe: RecipeEntity) {
    val localDensity = LocalDensity.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Colors.WHITE)
            .padding(horizontal = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            text = recipe.title,
            style = Typography.Title4,
            color = Colors.BLACK,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.GRAY)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val textTags = if (recipe.categories.isEmpty()) "Отсутсвуют"
            else "${recipe.categories.first().name.take(10)} + ${recipe.categories.size - 1}"
            CharactItem(
                icon = painterResource(id = R.drawable.ic_tag),
                text = textTags
            ) {

            }
            CharactItem(
                icon = painterResource(id = R.drawable.ic_time),
                text = recipe.timeCooking.convertCookingTime()
            )
            CharactItem(
                icon = painterResource(id = R.drawable.ic_difficulty),
                text = recipe.difficulty.toString()
            )
        }
    }
}

@Composable
private fun CharactItem(
    icon: Painter,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Colors.GRAY_DARK
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = Typography.Descr1,
            color = Colors.BLACK
        )
    }

}

@Composable
private fun Header(recipe: RecipeEntity) {
    val navigator = LocalNavigator.currentOrThrow
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.4f)
    ) {
        AppAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = recipe.image?.improveImageQuality(),
            contentDescription = recipe.title,
            contentScale = ContentScale.Crop
        )
        AppIconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp),
            icon = painterResource(id = R.drawable.ic_back)
        ) {
            navigator.pop()
        }
        AppIconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp),
            icon = if (recipe.isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite
        ) {

        }
    }
}
