package ru.topbun.detail_recipe

import ru.topbun.android.ScreenModelState

class DetailRecipeViewModel: ScreenModelState<DetailRecipeState>(DetailRecipeState()) {

    fun changeTabIndex(index: Int) = updateState { copy(selectedTabIndex = index) }

}