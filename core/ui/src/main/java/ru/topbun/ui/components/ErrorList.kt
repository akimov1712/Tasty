package ru.topbun.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography

@Composable
fun ErrorList(
    text: String,
    textButton: String = "Повторить",
    onClickButton: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.width(200.dp),
            text = text,
            style = Typography.General1.copy(fontSize = 18.sp),
            color = Colors.GRAY_DARK,
            textAlign = TextAlign.Center
        )
        AppButton(
            text = textButton,
            onClick = onClickButton
        )
    }
}