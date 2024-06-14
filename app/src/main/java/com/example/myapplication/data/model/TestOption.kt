package com.example.myapplication.data.model


data class TestOption(
    val id: Int,
    val label: String
)

val beckOptions = listOf(
    TestOption(1, "No"),
    TestOption(2, "Leve"),
    TestOption(3, "Moderado"),
    TestOption(4, "Bastante")
)

val hamAOptions = listOf(
    TestOption(1, "No presente"),
    TestOption(2, "Leve"),
    TestOption(3, "Moderado"),
    TestOption(4, "Grave"),
    TestOption(5, "Muy grave")
)

val gad7Options = listOf(
    TestOption(1, "Nunca"),
    TestOption(2, "Varios días"),
    TestOption(3, "La mitad de los días"),
    TestOption(4, "Casi cada día")
)
