package ru.topbun.add_recipe

import android.net.Uri

data class AddRecipeState(
    val imageUri: Uri? = null,
    val imageIsError: Boolean = false,
    val title: String = "",
    val titleIsError: Boolean = false,
    val description: String = "",
)
