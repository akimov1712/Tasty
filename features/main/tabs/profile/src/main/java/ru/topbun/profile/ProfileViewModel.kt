package ru.topbun.profile

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.android.FailedExtractTokenException
import ru.topbun.android.ScreenModelState
import ru.topbun.android.wrapperException
import ru.topbun.domain.useCases.auth.GetAccountInfoUseCase
import ru.topbun.domain.useCases.auth.LogoutUseCase
import ru.topbun.profile.ProfileState.ProfileScreenState

class ProfileViewModel(
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
): ScreenModelState<ProfileState>(ProfileState()) {

    fun logout() = screenModelScope.launch {
        logoutUseCase()
        updateState { copy(screenState = ProfileScreenState.NotAuth) }
    }

    fun getAccountInfo() = screenModelScope.launch{
        wrapperException({
            updateState { copy(screenState = ProfileScreenState.Loading) }
            val info = getAccountInfoUseCase()
            updateState { copy(screenState = ProfileScreenState.Success(info)) }
        }){ e, msg ->
            if (e is FailedExtractTokenException) updateState { copy(screenState = ProfileScreenState.NotAuth) }
            else updateState { copy(screenState = ProfileScreenState.Error(msg)) }
        }
    }

}