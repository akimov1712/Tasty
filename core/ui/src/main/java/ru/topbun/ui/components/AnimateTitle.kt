package ru.topbun.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography

@Composable
fun AnimateTitle(text: String, isTitleVisible: Boolean) {
    Column(
        modifier = Modifier.animateContentSize()
                then if (!isTitleVisible) Modifier.height(0.dp) else Modifier.wrapContentHeight()
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = text,
            style = Typography.Title1,
            color = Colors.BLACK
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}