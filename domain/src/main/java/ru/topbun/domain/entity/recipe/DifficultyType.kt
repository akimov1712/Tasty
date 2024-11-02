package ru.topbun.domain.entity.recipe

enum class DifficultyType {
    Easy, Medium, Hard;

    override fun toString(): String {
        return when(this){
            Easy -> "Легко"
            Medium -> "Нормально"
            Hard -> "Сложно"
        }
    }}