package ru.topbun.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import ru.topbun.common.convertCookingTime
import ru.topbun.common.toIngredients
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.ui.Colors
import ru.topbun.ui.R
import ru.topbun.ui.Typography
import ru.topbun.ui.components.RecipeList
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
            var text by rememberSaveable { mutableStateOf("") }
            var selectedIndex by rememberSaveable { mutableStateOf(0) }
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = "Рецепты",
                style = Typography.Title1,
                color = Colors.BLACK
            )
            Spacer(modifier = Modifier.height(20.dp))
            SearchTextField(
                value = text,
                onValueChange = { if (it.trim().length <= 64) text = it }
            )
            Spacer(modifier = Modifier.height(10.dp))
            TabRow(
                items = RecipeTabs.entries.map{ it.title },
                selectedIndex = selectedIndex
            ){ selectedIndex = it }
            Spacer(modifier = Modifier.height(20.dp))
            RecipeList(listOf())
        }
    }


}
