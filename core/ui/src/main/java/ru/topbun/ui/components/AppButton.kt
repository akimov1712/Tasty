package ru.topbun.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.topbun.ui.Colors
import ru.topbun.ui.Typography

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.BLUE,
            disabledContainerColor = Colors.BLUE.copy(0.65f)
        ),
        enabled = enabled || loading,
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        }
    ) {
        if (loading){
            CircularProgressIndicator(color = Colors.WHITE, strokeWidth = 2.5.dp, modifier = Modifier.size(20.dp))
        } else {
            Text(
                text = text,
                style = Typography.Title2,
                color = Colors.WHITE
            )
        }

    }
}