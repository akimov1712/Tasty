package ru.topbun.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography

@Composable
fun RowScope.BottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    title: String? = null,
    icon: Painter? = null,
) {
    val interaction = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(indication = null, interactionSource = interaction) {
                onClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val color = if(selected) Colors.BLUE else Colors.BLUE_GRAY
        icon?.let {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = it,
                contentDescription = title,
                tint = color
            )
        }
        title?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = it,
                style = Typography.Option1,
                color = color
            )
        }
    }
}
