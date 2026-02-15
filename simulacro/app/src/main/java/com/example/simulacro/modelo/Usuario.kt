package com.example.simulacro.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    @SerialName(value = "id")
    val id: String = "",
    @SerialName(value = "nombre")
    val nombre: String = "",
    @SerialName(value = "telefono")
    val telefono: String = "",

    @SerialName(value = "mensajes")
    val mensajes: List<Mensaje> = emptyList()
)