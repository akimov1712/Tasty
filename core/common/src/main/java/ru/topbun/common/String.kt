package ru.topbun.common

fun String.improveImageQuality() = this.replace("190x190", "1280x960")
fun String.validationEmail() = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$").matches(this)