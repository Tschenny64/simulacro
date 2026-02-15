package com.example.getconretrofitypatron.conexion

import com.example.getconretrofitypatron.modelo.Respuesta
import retrofit2.http.GET

interface StarWarsServicioApi {
    @GET("people")
    suspend fun obtenerPersonajes(): Respuesta
}