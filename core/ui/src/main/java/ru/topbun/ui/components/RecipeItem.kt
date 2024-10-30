package ru.topbun.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun RowScope.RecipeItem(recipe: RecipeEntity) {
    Column(
        Modifier.weight(1f)
    ) {
        RecipeItemImage(recipe)
        Spacer(modifier = Modifier.height(5.dp))
        Column(modifier = Modifier.padding(start = 3.dp)){
            Text(
                text = recipe.title,
                style = Typography.Title2,
                color = Colors.BLACK
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${recipe.timeCooking.convertCookingTime()} · ${recipe.ingredients.size.toIngredients()}",
                style = Typography.Descr1,
                color = Colors.BLUE_GRAY
            )
        }
    }

}

@Composable
private fun RecipeItemImage(recipe: RecipeEntity) {
    Box(
        contentAlignment = Alignment.Center
    ) {
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
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp)),
            model = recipe.image,
            contentScale = ContentScale.Crop,
            contentDescription = recipe.title,
            onState = {
                imageState = it
            },
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(0.65f))
                .clickable(
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() }
                ) { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = Colors.WHITE
            )
        }

    }
}