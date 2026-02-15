package com.example.getconretrofitypatron.datos

import com.example.getconretrofitypatron.conexion.StarWarsServicioApi
import com.example.getconretrofitypatron.modelo.Respuesta

interface PersonajesRepositorio {
    suspend fun obtenerPersonajes(): Respuesta
}

class ConexionPersonajesRepositorio(
    private val starWarsServicioApi: StarWarsServicioApi
) : PersonajesRepositorio {
    override suspend fun obtenerPersonajes(): Respuesta = starWarsServicioApi.obtenerPersonajes()
}