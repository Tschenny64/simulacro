package com.example.getconretrofitypatron.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Respuesta(
    @SerialName(value = "results")
    val resultados: List<Personaje> = emptyList()
)