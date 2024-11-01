package ru.topbun.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import ru.topbun.domain.entity.category.CategoryEntity
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography

@Composable
fun RowScope.CategoryItem(category: CategoryEntity) {
    Column(
        modifier = Modifier
            .weight(1f)
            .background(Colors.WHITE)
            .shadow(1.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        CategoryItemImage(category)
        Text(
            modifier = Modifier
                .background(Colors.WHITE)
                .padding(10.dp)
                .fillMaxWidth(),
            text = category.name,
            style = Typography.Title2,
            color = Colors.BLACK
        )
    }
}

@Composable
private fun CategoryItemImage(category: CategoryEntity) {
    Box(
        modifier = Modifier
            .background(Colors.WHITE),
        contentAlignment = Alignment.Center
    ) {
        var imageState by remember { mutableStateOf<AsyncImagePainter.State?>(null) }

        when(imageState){
            is AsyncImagePainter.State.Error -> { ImagePlaceholder() }
            is AsyncImagePainter.State.Loading -> CircularProgressIndicator(color = Colors.BLUE)
            else -> {}
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f),
            model = category.image,
            contentScale = ContentScale.Crop,
            contentDescription = category.name,
            onState = {
                imageState = it
            },
        )

    }
}
