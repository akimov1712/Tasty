package ru.topbun.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import ru.topbun.common.convertCookingTime
import ru.topbun.common.toIngredients
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.ui.Colors
import ru.topbun.ui.R
import ru.topbun.ui.Typography

@Composable
fun ColumnScope.RecipeList(recipes: List<RecipeEntity>) {
    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        items(recipes.chunked(2)){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                RecipeItem(it.first())
                RecipeItem(it.last())
            }
        }
    }
}

@Composable
private fun RowScope.RecipeItem(recipe: RecipeEntity) {
    Column(
        Modifier.weight(1f)
    ) {
        RecipeItemImage(recipe)
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.padding(start = 3.dp)){
            Text(
                text = recipe.title,
                style = Typography.Title2,
                color = Colors.BLACK
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${recipe.timeCooking.convertCookingTime()} • ${recipe.ingredients.size.toIngredients()}",
                style = Typography.Title2,
                color = Colors.BLACK
            )
        }
    }

}

@Composable
private fun RecipeItemImage(recipe: RecipeEntity) {
    Box{
        var imageState by remember { mutableStateOf<AsyncImagePainter.State?>(null) }
        val imagePlaceholder = @Composable { Icon(
            modifier = Modifier.size(64.dp, 50.dp),
            painter = painterResource(id = R.drawable.ic_image_placeholder),
            contentDescription = "Фото не загружено",
            tint = Colors.GRAY_DARK
        ) }

        when(imageState){
            AsyncImagePainter.State.Empty -> { imagePlaceholder() }
            is AsyncImagePainter.State.Error -> { imagePlaceholder() }
            is AsyncImagePainter.State.Loading -> CircularProgressIndicator(color = Colors.BLUE)
            else -> {}
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.85f)
                .clip(RoundedCornerShape(12.dp)),
            model = recipe.image,
            contentDescription = recipe.title,
            onState = {
                imageState = it
            },
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .size(28.dp)
                .clip(CircleShape)
                .background(Colors.BLACK.copy(0.65f)),
            onClick = {  }
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = Colors.WHITE
            )
        }

    }
}