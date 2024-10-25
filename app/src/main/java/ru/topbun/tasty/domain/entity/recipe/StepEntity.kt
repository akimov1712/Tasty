package ru.topbun.tasty.domain.entity.recipe

data class StepEntity(
    val id: Int,
    val text: String,
    val preview: String?,
    val order: Int,
)
