package ru.topbun.common


fun Int.toIngredients(): String{
    return "$this " + when{
        this == 1 -> "Ингредиент"
        this in (2..4) -> "Ингредиента"
        else -> "Ингредиентов"
    }
}

fun Int?.convertCookingTime(): String {
    if (this == null) return "Неизвестно"
    val days = this / (24 * 60)
    val hours = (this % (24 * 60)) / 60
    val remainingMinutes = this % 60

    val builder = StringBuilder()
    if (days > 0) {
        builder.append("${days} д ")
    }
    if (hours > 0) {
        builder.append("${hours} ч ")
    }
    if (remainingMinutes > 0) {
        builder.append("${remainingMinutes} мин")
    }
    val result = builder.toString().trim()

    return result.ifEmpty { "0 мин" }
}