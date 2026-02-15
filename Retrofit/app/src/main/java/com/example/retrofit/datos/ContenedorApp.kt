package com.example.retrofit.datos

import com.example.retrofit.conexion.ServicioApi
import com.example.retrofit.modelo.Personaje
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

interface ContenedorApp {
    val personajesRepositorio: PersonajesRepositorio
}

class StarWarsContenedorApp : ContenedorApp {
    private val baseUrl = "https://swapi.dev/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioRetrofit: ServicioApi by lazy {
        retrofit.create(ServicioApi::class.java)
    }

    override val personajesRepositorio: PersonajesRepositorio by lazy {
        ConexionPersonajesRepositorio(servicioRetrofit)
    }
}