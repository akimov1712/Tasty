package ru.topbun.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.topbun.ui.Colors
import ru.topbun.ui.R

@Composable
fun ImagePlaceholder(){
    Icon(
        modifier = Modifier.size(64.dp, 50.dp),
        painter = painterResource(id = R.drawable.ic_image_placeholder),
        contentDescription = "Фото не загружено",
        tint = Colors.GRAY_DARK
    )
}