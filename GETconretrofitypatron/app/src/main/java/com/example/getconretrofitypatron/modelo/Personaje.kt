package com.example.getconretrofitypatron.modelo

import androidx.compose.ui.graphics.Outline
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Personaje (
    @SerialName(value= "name")
    val nombre: String,
    @SerialName(value= "gender")
    val genero: String
)