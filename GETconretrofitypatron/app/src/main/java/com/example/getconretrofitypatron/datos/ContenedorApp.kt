package com.example.getconretrofitypatron.datos;

import com.example.getconretrofitypatron.conexion.StarWarsServicioApi;
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

import kotlinx.serialization.json.Json;
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit;
import kotlin.getValue

interface ContenedorApp {
    val personajesRepositorio: PersonajesRepositorio
}

class StarWarsContenedorApp : ContenedorApp {
    private val baseUrl = "https://swapi.dev/api/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true

    }

    private val retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()


    private val servicioRetrofit:StarWarsServicioApi by lazy {
        retrofit.create(StarWarsServicioApi::class.java)
    }

    override val personajesRepositorio: PersonajesRepositorio by lazy {
        ConexionPersonajesRepositorio(servicioRetrofit)
    }
}
