package ru.topbun.auth.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import ru.topbun.ui.Colors
import ru.topbun.ui.R.*
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AppTextField
import ru.topbun.ui.util.noRippleClickable

data object SignUpScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Регистрация",
                style = Typography.Title1,
                color = Colors.BLACK,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Fields()
            ButtonAccountExists()
            Spacer(modifier = Modifier.height(20.dp))
            ButtonSignUp()
        }
    }

}


@Composable
private fun ColumnScope.ButtonAccountExists(){
    Text(
        modifier = Modifier
            .padding(end = 5.dp, top = 5.dp)
            .align(Alignment.End)
            .noRippleClickable {

            },
        text = buildAnnotatedString {
            append("Уже есть аккаунт? ")
            pushStyle(SpanStyle(color = Colors.BLUE))
            append("Войти")
        },
        style = Typography.Tabs1,
        color = Colors.GRAY_DARK
    )
}

@Composable
private fun ButtonSignUp() {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Colors.BLUE),
        shape = RoundedCornerShape(8.dp),
        onClick = {

        }
    ) {
        Text(
            text = "Зарегистрироваться",
            style = Typography.Title2,
            color = Colors.WHITE
        )
    }
}

@Composable
private fun Fields() {
    AppTextField(
        modifier = Modifier.height(48.dp),
        value = "",
        onValueChange = {  },
        placeholder = "Логин",
    )
    Spacer(modifier = Modifier.height(10.dp))
    AppTextField(
        modifier = Modifier.height(48.dp),
        value = "",
        onValueChange = {  },
        placeholder = "Почта",
    )
    Spacer(modifier = Modifier.height(10.dp))
    AppTextField(
        modifier = Modifier.height(48.dp),
        value = "",
        onValueChange = { TODO() },
        placeholder = "Пароль",
//        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        iconButton = {
            IconButton(
                modifier = Modifier.size(28.dp),
                onClick = {
//                    showPassword = !showPassword
                }
            ) {
                Icon(
                    painter = painterResource(
                        drawable.ic_show
//                        if (showPassword) drawable.ic_show
//                        else drawable.ic_hide
                    ),
                    contentDescription = "Показать пароль",
                    tint = Colors.BLUE_GRAY
                )
            }
        }
    )
    Spacer(modifier = Modifier.height(10.dp))
    AppTextField(
        modifier = Modifier.height(48.dp),
        value = "",
        onValueChange = { TODO() },
        placeholder = "Подтвердите пароль",
//        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        iconButton = {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
//                    showPassword = !showPassword
                }
            ) {
                Icon(
                    painter = painterResource(
                        drawable.ic_show
//                        if (showPassword) drawable.ic_show
//                        else drawable.ic_hide
                    ),
                    contentDescription = "Показать пароль",
                    tint = Colors.BLUE_GRAY
                )
            }
        }
    )
}
