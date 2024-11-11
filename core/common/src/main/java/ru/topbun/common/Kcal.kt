package ru.topbun.common

fun calculateKcal(protein: Float?, carbs: Float?, fat: Float?): Int?{
    return if (listOf(protein,carbs, fat).contains(null)) null
    else  (4 * protein!! + 4 * carbs!! + 9 * fat!!).toInt()
}
