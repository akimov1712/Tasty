package ru.topbun.ui.util

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun localWidth(width: Float): Dp{
    val localDensity = LocalDensity.current
    return with(localDensity){
        width.toDp()
    }
}