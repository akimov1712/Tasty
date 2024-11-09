package ru.topbun.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import ru.topbun.ui.Colors
import ru.topbun.ui.R

@Composable
fun ImagePlaceholder(modifier: Modifier = Modifier,){
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.ic_image_placeholder),
        contentDescription = "Фото не загружено",
        tint = Colors.GRAY_DARK
    )
}