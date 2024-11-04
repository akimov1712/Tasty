package ru.topbun.auth.fragments.login

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.topbun.auth.fragments.SignUpScreen
import ru.topbun.auth.fragments.login.LoginState.LoginScreenState.Loading
import ru.topbun.ui.Colors
import ru.topbun.ui.R.*
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AppTextField
import ru.topbun.ui.util.noRippleClickable

data object LoginScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val viewModel = koinScreenModel<LoginViewModel>()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Вход",
                style = Typography.Title1,
                color = Colors.BLACK,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Fields(viewModel)
            ButtonAccountNotExists()
            Spacer(modifier = Modifier.height(20.dp))
            ButtonLogin(viewModel)
        }
    }

}

@Composable
private fun ColumnScope.ButtonAccountNotExists(){
    val navigator = LocalNavigator.currentOrThrow
    Text(
        modifier = Modifier
            .padding(end = 5.dp, top = 5.dp)
            .align(Alignment.End)
            .noRippleClickable {
                navigator.replace(SignUpScreen)
            },
        text = "Регистрация",
        style = Typography.Tabs1,
        color = Colors.BLUE
    )
}

@Composable
private fun ButtonLogin(viewModel: LoginViewModel) {
    val state by viewModel.state.collectAsState()
    val screenState = state.loginScreenState
    val enabled = screenState != Loading && state.validFields
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.BLUE.copy(
                alpha = if (!enabled) 0.65f else 1f
            )
        ),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        onClick = {
            viewModel.login()
        }
    ) {
        if (screenState == Loading){
            CircularProgressIndicator(color = Colors.BLUE, strokeWidth = 2.dp, modifier = Modifier.size(16.dp))
        } else {
            Text(
                text = "Войти",
                style = Typography.Title2,
                color = Colors.WHITE
            )
        }

    }
}

@Composable
private fun Fields(viewModel: LoginViewModel) {
    val state by viewModel.state.collectAsState()
    AppTextField(
        modifier = Modifier.height(48.dp),
        value = state.email,
        onValueChange = { if (it.length <= 40) viewModel.changeEmail(it) },
        placeholder = "Почта",
    )
    Spacer(modifier = Modifier.height(10.dp))
    AppTextField(
        modifier = Modifier.height(48.dp),
        value = state.password,
        onValueChange = { if (it.length <= 48) viewModel.changePassword(it) },
        placeholder = "Пароль",
        visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        iconButton = {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { viewModel.changePasswordVisible() }
            ) {
                Icon(
                    painter = painterResource(
                        if (state.showPassword) drawable.ic_show
                        else drawable.ic_hide
                    ),
                    contentDescription = "Показать пароль",
                    tint = Colors.BLUE_GRAY
                )
            }
        }
    )
}
