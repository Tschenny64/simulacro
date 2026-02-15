package com.example.retrofit.datos

import com.example.retrofit.conexion.ServicioApi
import com.example.retrofit.modelo.Respuesta

interface PersonajesRepositorio {
    suspend fun obtenerPersonajes(): Respuesta
}

class ConexionPersonajesRepositorio(
    private val starWarsServicioApi: ServicioApi
) : PersonajesRepositorio {
    override suspend fun obtenerPersonajes(): Respuesta = starWarsServicioApi.obtenerPersonaje()
}