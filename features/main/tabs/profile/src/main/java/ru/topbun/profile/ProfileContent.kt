package ru.topbun.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnitType.Companion.Sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.topbun.domain.entity.auth.UserEntity
import ru.topbun.navigation.main.MainScreenNavigator
import ru.topbun.navigation.main.MainScreenProvider
import ru.topbun.ui.Colors
import ru.topbun.ui.R.*
import ru.topbun.ui.Typography
import ru.topbun.ui.components.AppButton
import ru.topbun.ui.components.ErrorComponent
import ru.topbun.ui.util.noRippleClickable
import ru.topbun.ui.util.rippleClickable

data object ProfileScreen: Screen{

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 24.dp, end = 12.dp)
        ) {
            val viewModel = koinScreenModel<ProfileViewModel>()
            val mainNavigator = koinScreenModel<MainScreenNavigator>()
            viewModel.getAccountInfo()
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = "Профиль",
                style = Typography.Title1,
                color = Colors.BLACK
            )
            Spacer(modifier = Modifier.height(20.dp))
            CardContent(viewModel, mainNavigator)
        }
    }
}

@Composable
private fun CardContent(viewModel: ProfileViewModel, navigator: MainScreenNavigator) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(Colors.WHITE, RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center,
    ){
        when(val screenState = state.screenState){
            is ProfileState.ProfileScreenState.Error -> ErrorContent(screenState.msg){ viewModel.getAccountInfo() }
            ProfileState.ProfileScreenState.Loading -> LoadingContent()
            is ProfileState.ProfileScreenState.NotAuth -> NotAuthContent("Вы не авторизованы"){
                val authScreen =  ScreenRegistry.get(MainScreenProvider.Auth)
                navigator.pushScreen(authScreen)
            }
            is ProfileState.ProfileScreenState.Success -> SuccessContent(screenState.user){
                viewModel.logout()
            }
        }
    }
}


@Composable
private fun SuccessContent(user: UserEntity, onClickLogOut: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        IconWithText(drawable.ic_username, user.username)
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Colors.GRAY))
        IconWithText(drawable.ic_email, user.email)
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Colors.GRAY))
        IconWithText(drawable.ic_logout, "Выйти", Colors.RED, onClickLogOut)
    }
}

@Composable
private fun IconWithText(iconRes: Int, text: String, color: Color = Colors.BLACK, onClick: (() -> Unit)? = null) {
    val clickable = if(onClick == null) Modifier else Modifier.rippleClickable { onClick() }
    Row(
        modifier = Modifier.fillMaxWidth().then(clickable).padding(vertical = 20.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = iconRes),
            contentDescription = text,
            tint = color
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = text,
            style = Typography.Title3,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun LoadingContent() {
    CircularProgressIndicator(color = Colors.BLUE)
}

@Composable
private fun ErrorContent(text: String, onClickRetry: () -> Unit) {
    ErrorComponent(
        text = text,
        onClickButton = onClickRetry
    )
}

@Composable
private fun NotAuthContent(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = Typography.General1.copy(fontSize = 20.sp),
            color = Colors.GRAY_DARK,
            textAlign = TextAlign.Center
        )
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            text = "Авторизоваться",
            onClick = onClick
        )
    }
}