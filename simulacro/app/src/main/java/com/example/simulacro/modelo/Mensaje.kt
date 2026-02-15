package com.example.simulacro.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Mensaje(

    @SerialName("enviado")
    val enviado: Boolean = false,

    @SerialName("mensaje")
    val mensaje: String = ""
)