package ru.topbun.add_recipe

import android.net.Uri
import cafe.adriel.voyager.core.model.StateScreenModel
import ru.topbun.android.ScreenModelState

class AddRecipeViewModel: ScreenModelState<AddRecipeState>(AddRecipeState()){

    fun changeImage(uri: Uri?) = updateState { copy(imageUri = uri) }
    fun changeTitle(title: String) = updateState { copy(title = title, titleIsError = title.length < 8) }
    fun changeDescr(descr: String) = updateState { copy(description = descr) }


}